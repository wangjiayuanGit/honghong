package com.honghong.service.impl;

import com.honghong.common.CommentType;
import com.honghong.common.ResponseData;
import com.honghong.model.topic.CommentDO;
import com.honghong.model.topic.CommentDTO;
import com.honghong.model.topic.CommentResult;
import com.honghong.model.topic.TopicDO;
import com.honghong.model.user.UserDO;
import com.honghong.repository.CommentRepository;
import com.honghong.repository.LikeRepository;
import com.honghong.repository.TopicRepository;
import com.honghong.service.CommentService;
import com.honghong.util.PageUtils;
import com.honghong.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    private LikeRepository likeRepository;

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
    public ResponseData list(Long userId, PageUtils pageUtils) {
        if (userId == null) {
            return ResultUtils.paramError();
        }

        Page<TopicDO> page = topicRepository.findAllByUserId(userId, pageUtils.getSortPageRequest(new Sort(Sort.Direction.DESC, "createdAt")));
        List<TopicDO> topicDOS = page.getContent();
        List<Long> topicIds = new ArrayList<>();
        for (TopicDO topicDO : topicDOS) {
            topicIds.add(topicDO.getId());
        }
        List<CommentDO> all = commentRepository.findAllByTopicIdIn(topicIds);
        List<CommentResult> commentResults = new ArrayList<>();
        for (TopicDO topicDO : topicDOS) {
            List<CommentDO> commentDOS = new ArrayList<>();
            CommentResult commentResult = null;
            for (CommentDO commentDO : all) {
                if (commentDO.getTopicId().equals(topicDO.getId())) {
                    commentDOS.add(commentDO);
                }
            }
            commentResult = new CommentResult(commentDOS, topicDO);
            commentResults.add(commentResult);
        }

        for (CommentDO commentDO : all) {
            commentDO.setIsRead(true);
        }
        commentRepository.saveAll(all);
        return ResultUtils.success(commentResults);

    }
}
