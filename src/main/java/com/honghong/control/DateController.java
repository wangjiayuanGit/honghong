package com.honghong.control;

import com.honghong.common.Login;
import com.honghong.common.ResponseData;
import com.honghong.model.DataDTO;
import com.honghong.model.Response;
import com.honghong.service.DataService;
import com.honghong.util.DateUtils;
import com.honghong.util.PageUtils;
import com.honghong.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public ResponseData upload(HttpServletRequest request) {
        try {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartHttpServletRequest.getFile("file");
            if (file == null || file.isEmpty()) {
                return ResultUtils.paramError();
            }
            return dataService.upload(file.getInputStream(), file.getOriginalFilename());
        } catch (RuntimeException re) {
            return ResultUtils.customerRuntimeException(re.getMessage());
        } catch (Exception ex) {
            return ResultUtils.serverBusy();
        }
    }

}
