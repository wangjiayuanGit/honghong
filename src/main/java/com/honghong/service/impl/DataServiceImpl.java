package com.honghong.service.impl;

import com.honghong.common.ResponseCode;
import com.honghong.common.ResponseData;
import com.honghong.dao.DataDAO;
import com.honghong.model.DataDTO;
import com.honghong.model.TopicUserDTO;
import com.honghong.model.topic.CommentDO;
import com.honghong.model.topic.LikeDO;
import com.honghong.model.topic.TopicDO;
import com.honghong.model.topic.TopicDTO;
import com.honghong.model.user.UserDO;
import com.honghong.repository.CommentRepository;
import com.honghong.repository.LikeRepository;
import com.honghong.repository.TopicRepository;
import com.honghong.repository.UserRepository;
import com.honghong.service.DataService;
import com.honghong.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author ：wangjy
 * @description ：后台数据
 * @date ：2019/11/28 10:49
 */
@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private DataDAO dataDAO;

    @Override
    public ResponseData defaultData(DataDTO dataDTO) {
        Date date = dataDTO.getDate();
        String city = dataDTO.getCity();
        if (date.getDay() == new Date().getDay()) {
            date = new Date();
        } else {
            date = DateUtils.getEndOfDay(date);
        }
        Map<String, Object> map = new HashMap<>();
        //真实的话题数量
        Integer realData = topicRepository.countByTruthAndCreatedAtAfterAndCreatedAtBefore(true, DateUtils.getStartOfDay(date), date);
        //假的话题数量
        Integer bumData = topicRepository.countByTruthAndCreatedAtAfterAndCreatedAtBefore(false, DateUtils.getStartOfDay(date), date);
        Integer allData = realData + bumData;
        List<UserDO> userDOS = userRepository.findAllByLastLoginTimeAfterAndLastLoginTimeBefore(DateUtils.getStartOfDay(date), date);
        //当天在线人数
        Integer onlineUsers = userDOS.size();
        //授权登录的人数
        Integer authUser = userRepository.countByWechatOpenidIsNotNull();

        map.put("realData", realData);
        map.put("bumData", bumData);
        map.put("allData", allData);
        map.put("onlineUsers", onlineUsers);
        map.put("authUser", authUser);
        List<Map<String, Object>> mapList = dataDAO.showData(date, city);
        map.put("detail", mapList);
        return ResultUtils.success(map);
    }

    @Override
    public ResponseData dataList(String keyword, PageUtils pageUtils) {
        Specification<TopicDO> specification = (Specification<TopicDO>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate[] arr = new Predicate[predicates.size()];
            if (StringUtils.isNotBlank(keyword)) {
                Predicate predicate1 = criteriaBuilder.like(root.get("content"), "%" + keyword + "%");
                Predicate predicate2 = criteriaBuilder.like(root.get("nickname"), "%" + keyword + "%");
                Predicate predicate3 = criteriaBuilder.like(root.get("city"), "%" + keyword + "%");
                predicates.add(criteriaBuilder.or(predicate1, predicate2,predicate3));
            }

            query.where(predicates.toArray(arr));
            query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
            return query.getRestriction();
        };
        Page<TopicDO> page = topicRepository.findAll(specification, pageUtils.getPageRequest());
        return ResultUtils.success(page);
    }

    @Override
    public ResponseData delData(Long topicId) {
        if (topicId == null) {
            return ResultUtils.paramError();
        }
        Optional<TopicDO> byId = topicRepository.findById(topicId);
        if (!byId.isPresent()) {
            return ResultUtils.dataNull();
        }
        topicRepository.delete(byId.get());
        return ResultUtils.success();
    }

    @Override
    public ResponseData addData(TopicDTO topicDTO) {
        TopicDO topicDO = new TopicDO();
        topicDO.addTopic(topicDTO);
        topicDO = topicRepository.save(topicDO);
        return ResultUtils.success(topicDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData updateData(Long topicId, Integer likeNum) {
        if (topicId == null || likeNum < 0) {
            return ResultUtils.paramError();
        }
        UserDO userDO = new UserDO();
        userDO.setId(2L);
        Optional<TopicDO> byId = topicRepository.findById(topicId);
        TopicDO topicDO = byId.orElseThrow(()->new RuntimeException("未查找到数据"));
        if (likeNum < topicDO.getLikeSum()) {
            throw new RuntimeException("你不能减少数据点赞数量");
        }
        LikeDO likeDO = new LikeDO();
        likeDO.setTopicId(topicId);
        likeDO.setUser(userDO);
        likeDO.setNum(likeNum-topicDO.getLikeSum());
        likeDO.setCreatedAt(new Date());
        likeDO.setUpdatedAt(new Date());
        likeDO.setState(0);
        topicDO.setLikeSum(likeNum);
        topicDO.setLastCommentUser("热心网友");
        topicDO = topicRepository.saveAndFlush(topicDO);
        likeRepository.save(likeDO);
        return ResultUtils.success(topicDO);
    }

    @Override
    public ResponseData leaderBoard(PageUtils pageUtils) {
        Sort sort = new Sort(Sort.Direction.DESC, "likeSum");
        Page<TopicDO> page = topicRepository.findAll(pageUtils.getSortPageRequest(sort));
        List<TopicDO> list = page.getContent();
        int ranking = (pageUtils.getPage() - 1) * pageUtils.getSize() + 1;
        for (TopicDO topicDO : list) {
            topicDO.setRanking(ranking++);
        }
        return ResultUtils.success(page);
    }

    @Override
    public ResponseData upload(InputStream in, String fileName) {
        List<ExcelHead> excelHeads = new TopicUserDTO().creatHeader();
        List<TopicUserDTO> topicUserDTOS = null;
        try {
            topicUserDTOS = ParseExcelUtils.readExcelToEntity(TopicUserDTO.class, in, fileName, excelHeads);
//TODO 待优化
            
            topicUserDTOS.parallelStream().forEach(topicUserDTO -> {
                UserDO userDO = new UserDO();
                TopicDO topicDO = new TopicDO();
                userDO.fakeData(topicUserDTO.getNickName(), topicUserDTO.getHeadImage(), topicUserDTO.getCity(), topicUserDTO.getCreatedAt());
                userDO = userRepository.save(userDO);
                topicDO.fakeData(userDO, topicUserDTO.getContent(), topicUserDTO.getCommentSum(), topicUserDTO.getLikeSum());
                topicRepository.save(topicDO);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.common(400, e.getMessage());
        }
        return ResultUtils.success();
    }


}
