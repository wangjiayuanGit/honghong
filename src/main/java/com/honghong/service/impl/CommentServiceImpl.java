package com.honghong.service.impl;

import com.honghong.common.CommentType;
import com.honghong.common.ResponseData;
import com.honghong.model.topic.CommentDO;
import com.honghong.model.topic.CommentDTO;
import com.honghong.model.topic.TopicDO;
import com.honghong.model.user.UserDO;
import com.honghong.repository.CommentRepository;
import com.honghong.repository.TopicRepository;
import com.honghong.service.CommentService;
import com.honghong.util.PageUtils;
import com.honghong.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author ：wangjy
 * @description ：评论
 * @date ：2019/9/23 17:02
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public ResponseData addComment(CommentDTO commentDTO) {
        UserDO userDO = new UserDO();
        userDO.setId(commentDTO.getUserId());
        CommentDO commentDO = new CommentDO();
        commentDO.setUser(userDO);
        commentDO.setType(commentDTO.getType());
        commentDO.setOwnerId(commentDTO.getOwnerId());
        commentDO.setTopicId(commentDTO.getTopicId());
        commentDO.setContent(commentDTO.getContent());
        commentDO.setCreatedAt(new Date());
        commentDO.setUpdatedAt(new Date());
        commentDO.setState(0);
        commentDO.setIsRead(false);
        if (CommentType.TOPIC_COMMENT.equals(commentDTO.getType())) {
            Optional<TopicDO> byId = topicRepository.findById(commentDO.getOwnerId());
            if (byId.isPresent()) {
                TopicDO topicDO = byId.get();
                topicDO.setCommentSum(topicDO.getLikeSum() + 1);
                topicRepository.saveAndFlush(topicDO);
            }
        }
        commentRepository.save(commentDO);
        return ResultUtils.success(commentDO);
    }

    @Override
    public ResponseData delComment(Long id) {
        if (id == null) {
            ResultUtils.paramError();
        }
        Optional<CommentDO> byId = commentRepository.findById(id);
        if (!byId.isPresent()) {
            return ResultUtils.dataNull();
        }
        CommentDO commentDO = byId.get();
        commentDO.setState(-1);
        return ResultUtils.success();
    }

    @Override
    public ResponseData list(Long topicId, PageUtils pageUtils) {
        if (topicId == null) {
            return ResultUtils.paramError();
        }
        Optional<TopicDO> byId = topicRepository.findById(topicId);
        if (!byId.isPresent()) {
            throw new RuntimeException("数据异常");
        }
        PageRequest pageRequest = pageUtils.getPageRequest();
        Page<CommentDO> all = commentRepository.findAllByTopicIdAndStateIsNot(topicId, -1, pageRequest);
        List<CommentDO> list = all.getContent();
        for (CommentDO commentDO : list) {
            commentDO.setIsRead(true);
        }
        commentRepository.saveAll(list);
        return ResultUtils.success(all);

    }
}
