package com.honghong.control;

import com.honghong.common.ResponseData;
import com.honghong.model.topic.TopicDTO;
import com.honghong.service.TopicService;
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
 * @description ：话题
 * @date ：2019/9/18 16:55
 */
@RestController
@Api(description = "话题")
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @PostMapping("/add")
    @ApiOperation("添加")
    public ResponseData addTopic(@Valid TopicDTO topicDTO) {
        return topicService.addTopic(topicDTO);
    }

    @PostMapping("/del")
    @ApiOperation("/删除")
    public ResponseData delTopic(Long topicId) {
        return topicService.delTopic(topicId);
    }

    @PostMapping("topicList")
    @ApiOperation("话题列表")
    public ResponseData topicList(String keyword, String city, PageUtils pageUtils) {
        return topicService.topicList(keyword, city, pageUtils);
    }

    @ApiOperation("同一个用户的话题列表")
    @GetMapping("/oneUserList")
    public ResponseData oneUserList(Long userId, PageUtils pageUtils) {
        return topicService.oneUserList(userId, pageUtils);
    }

    @ApiOperation("排行榜")
    @GetMapping("/leaderBoard")
    public ResponseData leaderBoard(Long userId, Integer dayOrMonth, PageUtils pageUtils) {
        return topicService.leaderBoard(userId, dayOrMonth, pageUtils);
    }

    @GetMapping("/search")
    @ApiOperation("首页搜素")
    public ResponseData search(String keyword, PageUtils pageUtils) {
        return topicService.search(keyword, pageUtils);
    }

    @GetMapping("/detail")
    @ApiOperation("话题详细信息")
    public ResponseData detail(Long id) {
        return topicService.detail(id);
    }

    @GetMapping("orthterTopicList")
    @ApiOperation("别人的个人中心")
    public ResponseData other(Long userId, PageUtils pageUtils) {
        return topicService.other(userId, pageUtils);
    }
}
