package com.honghong.service;

import com.honghong.common.ResponseData;
import com.honghong.model.DataDTO;
import com.honghong.model.topic.TopicDTO;
import com.honghong.util.PageUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Date;

/**
 * @author ：wangjy
 * @description ：后台数据service
 * @date ：2019/11/28 9:14
 */
public interface DataService {
    /**
     * 页面整体数据
     *
     * @param date
     * @param city
     * @return
     */
    ResponseData defaultData(DataDTO dataDTO);

    /**
     * 数据列表
     *
     * @param keyword
     * @param pageUtils
     * @return
     */
    ResponseData dataList(String keyword, PageUtils pageUtils);

    /**
     * 删除
     *
     * @param topicId
     * @return
     */
    ResponseData delData(Long topicId);

    /**
     * 添加数据
     *
     * @param topicDTO
     * @return
     */
    ResponseData addData(TopicDTO topicDTO);

    /**
     * 编辑数据
     *
     * @param likeNum
     * @param topicId
     * @return
     */
    ResponseData updateData(Long topicId, Integer likeNum);

    /**
     * 大排行
     *
     * @param pageUtils
     * @return
     */
    ResponseData leaderBoard(PageUtils pageUtils);

    /**
     * 数据导入
     *
     * @param in
     * @param fileName
     * @return
     */
    ResponseData upload(InputStream in, String fileName);
}
