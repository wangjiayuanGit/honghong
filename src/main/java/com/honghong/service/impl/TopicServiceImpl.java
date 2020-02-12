package com.honghong.service.impl;

import com.honghong.common.ResponseData;
import com.honghong.model.topic.TopicDO;
import com.honghong.model.topic.TopicDTO;
import com.honghong.model.user.UserDO;
import com.honghong.repository.TopicRepository;
import com.honghong.repository.UserRepository;
import com.honghong.service.TopicService;
import com.honghong.util.AppearanceRate;
import com.honghong.util.DataUtils;
import com.honghong.util.PageUtils;
import com.honghong.util.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
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
        PageRequest page = pageUtils.getPageRequest();
        Page<TopicDO> topicDOS = topicRepository.findAllByUserId(userId, page);
        return ResultUtils.success(topicDOS);
    }

    @Override
    public ResponseData leaderBoard(Long userId, Integer dayOrMonth, PageUtils pageUtils) {
        if (dayOrMonth == null || userId == null) {
            return ResultUtils.paramError();
        }
        Map<String, Object> map = new HashMap<>();
        Specification<TopicDO> specificationAll = specificationBuild(null, dayOrMonth, true);
        Page<TopicDO> all = topicRepository.findAll(specificationAll, pageUtils.getSortPageRequest(new Sort(Sort.Direction.DESC, "createdAt")));
        map.put("all", all);
        int ranking = (pageUtils.getPage() - 1) * pageUtils.getSize();
        for (int i = 0; i < all.getContent().size(); i++) {
            ranking++;
            all.getContent().get(i).setRanking(ranking);
        }
        Specification<TopicDO> specificationMy = specificationBuild(userId, dayOrMonth, false);
        List<TopicDO> createdAt = topicRepository.findAll(specificationMy, new Sort(Sort.Direction.DESC, "createdAt"));
        TopicDO myTopic = null;
        if (createdAt != null && createdAt.size() >= 1) {
            myTopic = createdAt.get(0);
            Specification<TopicDO> serviceSpecification = specificationBuild(null, dayOrMonth, true);
            List<TopicDO> list = topicRepository.findAll(serviceSpecification, new Sort(Sort.Direction.DESC, "createdAt"));
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
    public void ranking() {
        PageUtils pageUtils = new PageUtils();
        Sort sort = new Sort(Sort.Direction.DESC, "likeSum");
        PageRequest page = pageUtils.getSortPageRequest(sort);
        Page<TopicDO> doPage = topicRepository.findAll(page);
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
        Date now = new Date();
        //过去一天的时间
        Calendar day = Calendar.getInstance();
        day.setTime(new Date());
        day.add(Calendar.HOUR, -24);
        Date oneDay = day.getTime();
        //过去一个月的时间
        Calendar month = Calendar.getInstance();
        month.setTime(new Date());
        month.add(Calendar.MONTH, -1);
        Date oneMonth = month.getTime();
        return (Specification<TopicDO>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate[] arr = new Predicate[predicates.size()];
            Predicate predicate = criteriaBuilder.conjunction();
            if (dayOrMonth == 0) {
                predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt").as(Date.class), oneDay));
            } else {
                predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt").as(Date.class), oneMonth));
            }
            predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt").as(Date.class), now));
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