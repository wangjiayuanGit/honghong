package com.honghong.service.impl;

import com.honghong.common.ResponseData;
import com.honghong.model.topic.LikeDO;
import com.honghong.model.topic.TopicDO;
import com.honghong.model.user.UserDO;
import com.honghong.repository.LikeRepository;
import com.honghong.repository.TopicRepository;
import com.honghong.service.LikeService;
import com.honghong.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        topicRepository.save(topicDO);
        likeRepository.save(likeDO);
        return ResultUtils.success();
    }

    @Override
    public ResponseData likeList(Long topicId) {
        if (topicId == null) {
            return ResultUtils.paramError();
        }
        List<LikeDO> likeDOS = likeRepository.findAllByTopicId(topicId);
        return ResultUtils.success(likeDOS);
    }
}
