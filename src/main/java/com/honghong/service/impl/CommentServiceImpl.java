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
import com.honghong.repository.UserRepository;
import com.honghong.service.CommentService;
import com.honghong.util.PageUtils;
import com.honghong.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private UserRepository userRepository;

    @Override
    public ResponseData addComment(CommentDTO commentDTO) {
        Optional<UserDO> optional = userRepository.findById(commentDTO.getUserId());
        UserDO userDO = optional.orElseThrow(() -> new RuntimeException("数据异常"));
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
        commentDO = commentRepository.save(commentDO);
        List<CommentDO> allByTopicIdAndTypeIs = commentRepository.findAllByTopicIdAndTypeIs(commentDTO.getTopicId(), CommentType.TOPIC_COMMENT);
        Set<UserDO> userDOS = new HashSet<>();
        for (CommentDO commentDO1 : allByTopicIdAndTypeIs) {
            userDOS.add(commentDO1.getUser());
        }
        if (CommentType.TOPIC_COMMENT.equals(commentDTO.getType())) {
            Optional<TopicDO> byId = topicRepository.findById(commentDO.getOwnerId());
            TopicDO topicDO = byId.orElseThrow(() -> new RuntimeException("数据异常"));
            topicDO.setCommentSum(topicDO.getCommentSum() + 1);
            topicDO.setLastCommentUser(userDO.getNickname());
            topicDO.setCommentUserNum(userDOS.size());
            topicRepository.saveAndFlush(topicDO);
        }

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
            if (commentDOS.size() > 0) {
                commentResult = new CommentResult(commentDOS, topicDO);
            }
            if (commentResult != null) {
                commentResults.add(commentResult);
            }
        }

        for (CommentDO commentDO : all) {
            commentDO.setIsRead(true);
        }
        commentRepository.saveAll(all);
        return ResultUtils.success(commentResults);

    }
}
