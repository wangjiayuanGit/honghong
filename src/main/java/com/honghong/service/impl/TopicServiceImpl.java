package com.honghong.service.impl;

import com.honghong.common.ResponseData;
import com.honghong.model.topic.CommentDO;
import com.honghong.model.topic.TopicDO;
import com.honghong.model.topic.TopicDTO;
import com.honghong.model.user.UserDO;
import com.honghong.repository.CommentRepository;
import com.honghong.repository.TopicRepository;
import com.honghong.repository.UserRepository;
import com.honghong.service.TopicService;
import com.honghong.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ：wangjy
 * @description ：话题实现
 * @date ：2019/9/9 16:32
 */
@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public ResponseData addTopic(TopicDTO topicDTO) {
        Optional<UserDO> byId = userRepository.findById(topicDTO.getUserId());
        UserDO userDO = byId.orElseThrow(() -> new RuntimeException("该用户不存在"));
        TopicDO topicDO = new TopicDO();
        topicDO.setUser(userDO);
        topicDO.addTopic(topicDTO);
//        if ("#".startsWith(topicDTO.getTitle())) {
//            topicDO.setType(TopicType.DEMANDS);
//        } else if ("*".startsWith(topicDTO.getTitle())) {
//            topicDO.setType(TopicType.NON_DEMAND);
//        } else {
//            topicDO.setType(TopicType.OTHER);
//        }
        topicDO = topicRepository.save(topicDO);
        return ResultUtils.success(topicDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData delTopic(Long topicId) {
        Optional<TopicDO> byId = topicRepository.findById(topicId);
        TopicDO topicDO = byId.orElseThrow(() -> new RuntimeException("该topic不存在"));
        topicRepository.delete(topicDO);
        return ResultUtils.success();
    }

    @Override
    public ResponseData topicList(String keyword, String city, PageUtils pageUtils) {
        Specification<TopicDO> specification = (Specification<TopicDO>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate[] arr = new Predicate[predicates.size()];
            if (StringUtils.isNoneEmpty(city)) {
                predicates.add(criteriaBuilder.like(root.get("city"), "%" + city + "%"));
            }
            query.where(predicates.toArray(arr));
            query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
            return query.getRestriction();
        };

        List<TopicDO> list = topicRepository.findAll(specification);
        List<TopicDO> result = getList(list);
        return ResultUtils.success(DataUtils.randomList(result));
    }

    @Override
    public ResponseData oneUserList(Long userId, PageUtils pageUtils) {
        if (userId == null) {
            return ResultUtils.paramError();
        }
        ranking();
        UserDO userDO = new UserDO();
        userDO.setId(userId);
        Map<String, Object> map = new HashMap<>();
        PageRequest page = pageUtils.getSortPageRequest(new Sort(Sort.Direction.DESC, "createdAt"));
        Page<TopicDO> topicDOS = topicRepository.findAllByUserId(userId, page);
        List<TopicDO> list = topicRepository.findAllByUserId(userId);

        List<Long> topicIds = new ArrayList<>();
        for (TopicDO topic : list) {
            topicIds.add(topic.getId());
        }
        Integer unreadMessage = commentRepository.countByTopicIdInAndIsReadIs(topicIds, false);
//        Integer unreadMessage = commentRepository.countByUserAndIsReadIs(userDO, false);
        map.put("topicDOS", topicDOS);
        map.put("unreadMessage", unreadMessage);
        return ResultUtils.success(map);
    }

    @Override
    public ResponseData leaderBoard(Long userId, Integer dayOrMonth, PageUtils pageUtils) {
        if (dayOrMonth == null || userId == null) {
            return ResultUtils.paramError();
        }
        Map<String, Object> map = new HashMap<>();
        //先找出排行榜列表
        Specification<TopicDO> specificationAll = specificationBuild(null, dayOrMonth, true);
//        Page<TopicDO> all = topicRepository.findAll(specificationAll, pageUtils.getSortPageRequest(new Sort(Sort.Direction.DESC, "createdAt")));
        Page<TopicDO> all = topicRepository.findAll(specificationAll, pageUtils.getPageRequest());
        map.put("all", all);
        int ranking = (pageUtils.getPage() - 1) * pageUtils.getSize();
        for (int i = 0; i < all.getContent().size(); i++) {
            ranking++;
            all.getContent().get(i).setRanking(ranking);
        }
        //找到自己的topic列表
        Specification<TopicDO> specificationMy = specificationBuild(userId, dayOrMonth, false);
        List<TopicDO> createdAt = topicRepository.findAll(specificationMy, new Sort(Sort.Direction.DESC, "createdAt"));
        TopicDO myTopic = null;
        //如果自己发布的有topic数据（必须是当天或者当月的数据），设置排名
        if (createdAt != null && createdAt.size() >= 1) {
            myTopic = createdAt.get(0);
            //设置排名
            Specification<TopicDO> serviceSpecification = specificationBuild(null, dayOrMonth, true);
//            List<TopicDO> list = topicRepository.findAll(serviceSpecification, new Sort(Sort.Direction.DESC, "createdAt"));
            List<TopicDO> list = topicRepository.findAll(serviceSpecification);
            int rankingAll = 0;
            for (TopicDO topicDO : list) {
                rankingAll++;
                if (myTopic.getId().equals(topicDO.getId())) {
                    myTopic.setRanking(rankingAll);
                }
            }
        }
        map.put("myTopic", myTopic);
        return ResultUtils.success(map);
    }

    @Override
    @Transactional
    public void ranking() {
        clear();
        PageUtils pageUtils = new PageUtils();
        Specification<TopicDO> specification = specificationBuild(null, 0, true);
        Page<TopicDO> doPage = topicRepository.findAll(specification, pageUtils.getPageRequest());
        List<TopicDO> topicDOS = doPage.getContent();
        int index = 1;
        for (TopicDO topicDO : topicDOS) {
            topicDO.setRankingOfTheDay(index++);
        }
        topicRepository.saveAll(topicDOS);
    }

    @Override
    public ResponseData search(String keyword, PageUtils pageUtils) {
        Specification<TopicDO> specification = (Specification<TopicDO>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate[] arr = new Predicate[predicates.size()];
            if (StringUtils.isNotBlank(keyword)) {
                Predicate predicate1 = criteriaBuilder.like(root.get("nickname"), "%" + keyword + "%");
                Predicate predicate2 = criteriaBuilder.like(root.get("content"), "%" + keyword + "%");
                predicates.add(criteriaBuilder.or(predicate1, predicate2));
            }
            query.where(predicates.toArray(arr));
            query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
            return query.getRestriction();
        };
        Page<TopicDO> page = topicRepository.findAll(specification, pageUtils.getPageRequest());
        return ResultUtils.success(page);
    }

    @Override
    public ResponseData detail(Long id) {
        Map<String, Object> map = new HashMap<>();
        Optional<TopicDO> byId = topicRepository.findById(id);
        TopicDO topicDO = byId.orElseThrow(() -> new RuntimeException("数据不存在"));
        List<CommentDO> allByTopicId = commentRepository.findAllByTopicId(id);
        map.put("topicDD", topicDO);
        map.put("comment", allByTopicId);
        return ResultUtils.success(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clear() {
        topicRepository.updateRanking();
    }

    @Override
    public ResponseData other(Long userId, PageUtils pageUtils) {
        if (userId == null) {
            return ResultUtils.paramError();
        }
        ranking();
        Page<TopicDO> page = topicRepository.findAllByUserId(userId, pageUtils.getSortPageRequest(new Sort(Sort.Direction.DESC, "createdAt")));
        List<TopicDO> topicDOS = page.getContent();
        List<Long> topicIds = new ArrayList<>();
        for (TopicDO topicDO : topicDOS) {
            topicIds.add(topicDO.getId());
        }
        List<CommentDO> all = commentRepository.findAllByTopicIdIn(topicIds);
        for (TopicDO topicDO : topicDOS) {
            List<CommentDO> commentDOS = new ArrayList<>();

            for (CommentDO commentDO : all) {
                if (commentDO.getTopicId().equals(topicDO.getId())) {
                    commentDOS.add(commentDO);
                }
            }
            topicDO.setCommentDOS(commentDOS);
        }
        return ResultUtils.success(topicDOS);

    }

    //2020-03-03 23:27:20.593
    private List<TopicDO> getList(List<TopicDO> list) {
        //新数据
        List<TopicDO> newData = new ArrayList<>();
        //老数据
        List<TopicDO> oldData = new ArrayList<>();
        for (TopicDO topicDO : list) {
            if (DataUtils.isNewData(topicDO.getCreatedAt())) {
                newData.add(topicDO);
            } else {
                oldData.add(topicDO);
            }
        }
        newData = AppearanceRate.getList(newData);
        oldData = DataUtils.dieOut(oldData);
        oldData = AppearanceRate.getList(oldData, 100);
        List<TopicDO> result = new ArrayList<>();
        result.addAll(newData);
        result.addAll(oldData);
        return AppearanceRate.getResult(result, 3);
    }

    private Specification<TopicDO> specificationBuild(Long userId, Integer dayOrMonth, boolean needToSort) {
        return (Specification<TopicDO>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate[] arr = new Predicate[predicates.size()];
            Predicate predicate = criteriaBuilder.conjunction();
            if (dayOrMonth == 0) {
                predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt").as(Date.class), DateUtils.getStartOfDay(new Date())));
                predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt").as(Date.class), DateUtils.getEndOfDay(new Date())));
            } else {
                predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt").as(Date.class), DateUtils.getStartOfTheMonth()));
                predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt").as(Date.class), DateUtils.getEndOfTheMouth()));
            }
            if (userId != null) {
                UserDO userDO = new UserDO();
                userDO.setId(userId);
                predicates.add(criteriaBuilder.equal(root.get("user").as(UserDO.class), userDO));
            }
            predicates.add(predicate);
            query.where(predicates.toArray(arr));
            if (needToSort) {
                query.orderBy(criteriaBuilder.desc(root.get("likeSum")));
            }
            return query.getRestriction();
        };
    }
}