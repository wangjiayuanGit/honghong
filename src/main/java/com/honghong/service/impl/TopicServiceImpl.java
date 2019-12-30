package com.honghong.service.impl;

import com.honghong.common.ResponseData;
import com.honghong.model.topic.TopicDO;
import com.honghong.model.topic.TopicDTO;
import com.honghong.model.user.UserDO;
import com.honghong.repository.TopicRepository;
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

    @Override
    public ResponseData addTopic(TopicDTO topicDTO) {
        UserDO userDO = new UserDO();
        userDO.setId(topicDTO.getUserId());
        TopicDO topicDO = new TopicDO();
        topicDO.addTopic(topicDTO);
        topicDO.setUser(userDO);
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
            if (StringUtils.isNotBlank(keyword)) {
                Predicate predicate1 = criteriaBuilder.like(root.get("title"), "%" + keyword + "%");
                Predicate predicate2 = criteriaBuilder.like(root.get("content"), "%" + keyword + "%");
                predicates.add(criteriaBuilder.or(predicate1, predicate2));
            }
            if (StringUtils.isNoneEmpty(city)) {
                predicates.add(criteriaBuilder.like(root.get("city"), "%" + city + "%"));
            }
            query.where(predicates.toArray(arr));
            query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
            return query.getRestriction();
        };
        pageUtils.setSize(1000);
        PageRequest page = pageUtils.getPageRequest();
        Page<TopicDO> list = topicRepository.findAll(specification, page);
        List<TopicDO> content = list.getContent();
        List<TopicDO> result = getList(content);
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
        //返回数据
        Map<String, Object> map = new HashMap<>();
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

        Sort sort = new Sort(Sort.Direction.DESC, "likeSum");
        PageRequest page = pageUtils.getSortPageRequest(sort);
        Specification<TopicDO> specification = (Specification<TopicDO>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (dayOrMonth == 0) {
                predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt").as(Date.class), oneDay));
            } else {
                predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt").as(Date.class), oneMonth));
            }
            predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt").as(Date.class), now));
            return predicate;
        };
        Page<TopicDO> all = topicRepository.findAll(specification, page);
        //TODO 如果自己的排名在前十，或者当天或者本月没有发布话题，不添加第十一行
        int ranking = (pageUtils.getPage() - 1) * pageUtils.getSize();
        boolean hasMe = false;
        for (int i = 0; i < all.getContent().size(); i++) {
            ranking++;
            all.getContent().get(i).setRanking(ranking);
            if (userId.equals(all.getContent().get(i).getUser().getId())) {
                hasMe = true;
            }
        }
        map.put("all", all);
        if (!hasMe) {
            Specification<TopicDO> my = (Specification<TopicDO>) (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                Predicate[] arr = new Predicate[predicates.size()];
                Predicate predicate = criteriaBuilder.conjunction();
                if (dayOrMonth == 0) {
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt").as(Date.class), oneDay));
                } else {
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt").as(Date.class), oneMonth));
                }
                predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt").as(Date.class), now));
                UserDO userDO = new UserDO();
                userDO.setId(userId);
                predicates.add(criteriaBuilder.equal(root.get("user").as(UserDO.class), userDO));
                predicates.add(predicate);
                query.where(predicates.toArray(arr));
                query.orderBy(criteriaBuilder.desc(root.get("likeSum")));
                return query.getRestriction();
            };
            List<TopicDO> myTopicList = topicRepository.findAll(my);
            if (myTopicList.size() > 0) {
                Specification<TopicDO> specification1 = (Specification<TopicDO>) (root, query, criteriaBuilder) -> {
                    Predicate predicate = criteriaBuilder.conjunction();
                    if (dayOrMonth == 0) {
                        predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt").as(Date.class), oneDay));
                    } else {
                        predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt").as(Date.class), oneMonth));
                    }
                    predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt").as(Date.class), now));
                    query.where(predicate);
                    query.orderBy(criteriaBuilder.desc(root.get("likeSum")));
                    return query.getRestriction();
                };
                List<TopicDO> list = topicRepository.findAll(specification1);
                int rankingAll = 0;
                TopicDO myTopic = null;
                for (TopicDO topicDO : list) {
                    rankingAll++;
                    topicDO.setRanking(rankingAll);
                    if (userId.equals(topicDO.getUser().getId())) {
                        myTopic = topicDO;
                    }
                }
                map.put("myTopic", myTopic);
            }
        }

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
        return result;
    }
}