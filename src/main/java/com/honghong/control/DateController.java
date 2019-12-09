package com.honghong.control;

import com.honghong.common.Login;
import com.honghong.common.ResponseData;
import com.honghong.model.DataDTO;
import com.honghong.service.DataService;
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
 * @description ：后台数据
 * @date ：2019/12/1 0:50
 */
@RestController
@RequestMapping("/data")
@Api(description = "后台数据")
public class DateController {
    @Autowired
    private DataService dataService;

    @GetMapping("/default")
    @ApiOperation("/默认数据")
    @Login
    public ResponseData defaultData(@Valid DataDTO dataDTO) {
        return dataService.defaultData(dataDTO);
    }

    @PostMapping("/update")
    @ApiOperation("修改点赞数量")
    @Login
    public ResponseData updateData(Long topicId, Integer likeNum) {
        return dataService.updateData(topicId, likeNum);
    }

    @PostMapping("/del")
    @ApiOperation("删除数据")
    @Login
    public ResponseData delData(Long topicId) {
        return dataService.delData(topicId);
    }

    @PostMapping("/leaderBoard")
    @ApiOperation("排行榜")
    @Login
    public ResponseData leaderBoard(PageUtils pageUtils) {
        return dataService.leaderBoard(pageUtils);
    }

    @GetMapping("/dataList")
    @ApiOperation("数据列表")
    @Login
    public ResponseData dataList(String keyword, PageUtils pageUtils) {
        return dataService.dataList(keyword, pageUtils);
    }
}
