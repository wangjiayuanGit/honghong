package com.honghong.service;

import com.honghong.common.ResponseData;
import com.honghong.model.topic.CommentDTO;
import com.honghong.util.PageUtils;

/**
 * @author ：wangjy
 * @description ：评论
 * @date ：2019/9/23 17:01
 */
public interface CommentService {
    /**
     * 评论
     *
     * @param commentDTO
     * @return
     */
    ResponseData addComment(CommentDTO commentDTO);

    /**
     * 删除评论
     *
     * @param id
     * @return
     */
    ResponseData delComment(Long id);

    /**
     * 评论列表
     *
     * @param topicId
     * @param pageUtils
     * @return
     */
    ResponseData list(Long topicId, PageUtils pageUtils);
}
