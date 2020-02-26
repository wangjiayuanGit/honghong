package com.honghong.service.impl;

import com.honghong.common.ResponseData;
import com.honghong.model.topic.LikeDO;
import com.honghong.model.topic.LikeDTO;
import com.honghong.model.topic.LikeVO;
import com.honghong.model.topic.TopicDO;
import com.honghong.model.user.UserDO;
import com.honghong.repository.LikeRepository;
import com.honghong.repository.TopicRepository;
import com.honghong.service.LikeService;
import com.honghong.util.BeanMapUtils;
import com.honghong.util.JsonUtils;
import com.honghong.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ：wangjy
 * @description ：点赞
 * @date ：2019/9/19 17:56
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public ResponseData like(Long topicId, Long userId, Integer num) {
        if (topicId == null || userId == null || num == null) {
            return ResultUtils.paramError();
        }
        UserDO userDO = new UserDO();
        userDO.setId(userId);
        LikeDO likeDO = new LikeDO();
        likeDO.setTopicId(topicId);
        likeDO.setUser(userDO);
        likeDO.setNum(num);
        likeDO.setCreatedAt(new Date());
        likeDO.setUpdatedAt(new Date());
        likeDO.setState(0);
        Optional<TopicDO> byId = topicRepository.findById(topicId);
        if (!byId.isPresent()) {
            throw new RuntimeException("数据异常");
        }
        TopicDO topicDO = byId.get();
        Integer sum = topicDO.getLikeSum() + num;
        topicDO.setLikeSum(sum);
        topicDO.setUpdatedAt(new Date());
        topicRepository.save(topicDO);
        likeRepository.save(likeDO);
        return ResultUtils.success();
    }

    @Override
    public ResponseData likeList(Long userId) {
        if (userId == null) {
            return ResultUtils.paramError();
        }
        List<Map<String, Object>> allByUserId = likeRepository.findAllByUserId(userId);
        //将sql查询结果转为LikeVO
        List<LikeVO> likeVOS = new ArrayList<>();
        for (Map<String, Object> map : allByUserId) {
            try {
                LikeVO likeVO = BeanMapUtils.mapToBean(map, LikeVO.class);
                likeVOS.add(likeVO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //将同一个topicId 的对象包装成一个集合
        List<LikeDTO> likeDTOS = new ArrayList<>();
        Set<String> topicIds = new LinkedHashSet<>();
        for (LikeVO likeVO : likeVOS) {
            topicIds.add(likeVO.getTopicId().toString());
        }

        for (String topicId : topicIds) {
            List<LikeVO> list = new ArrayList<>();
            for (LikeVO likeVO : likeVOS) {
                if (likeVO.getTopicId().toString().equals(topicId)) {
                    list.add(likeVO);
                }
            }
            LikeDTO likeDTO = new LikeDTO(list);
            likeDTOS.add(likeDTO);
        }
        return ResultUtils.success(likeDTOS);
    }
}
