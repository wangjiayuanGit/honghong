package com.honghong.control;

import com.honghong.common.ResponseData;
import com.honghong.service.LikeService;
import com.honghong.util.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：wangjy
 * @description ：点赞
 * @date ：2019/9/19 18:09
 */
@RestController
@Api(description = "点赞")
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/like")
    @ApiOperation("点赞")
    public ResponseData like(Long topicId, Long userId, Integer num) {
        return likeService.like(topicId, userId, num);
    }

    @GetMapping("/list")
    @ApiOperation("点赞列表")
    public ResponseData likeList(Long userId, PageUtils pageUtils) {
        return likeService.likeList(userId, pageUtils);
    }
}
