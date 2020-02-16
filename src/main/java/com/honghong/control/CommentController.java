package com.honghong.control;

import com.honghong.common.ResponseData;
import com.honghong.model.topic.CommentDTO;
import com.honghong.service.CommentService;
import com.honghong.util.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author ：wangjy
 * @description ：评论
 * @date ：2019/9/23 17:45
 */
@RestController
@RequestMapping("/comment")
@Api(description = "评论")
public class  CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    @ApiOperation("/添加评论")
    public ResponseData addComment(@Valid CommentDTO commentDTO) {
        return commentService.addComment(commentDTO);
    }

    @PostMapping("/del")
    @ApiOperation("删除评论")
    public ResponseData delComment(Long id) {
        return commentService.delComment(id);
    }

    @GetMapping("/list")
    @ApiOperation("评论列表")
    public ResponseData list(Long topicId, PageUtils pageUtils) {
        return commentService.list(topicId, pageUtils);
    }
}
