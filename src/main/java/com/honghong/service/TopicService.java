package com.honghong.service;

import com.honghong.common.ResponseData;
import com.honghong.model.topic.TopicDTO;
import com.honghong.util.PageUtils;

/**
 * @author ：wangjy
 * @description ：话题 service
 * @date ：2019/9/9 11:08
 */
public interface TopicService {
    /**
     * 添加话题
     *
     * @param topicDTO
     * @return
     */
    ResponseData addTopic(TopicDTO topicDTO);

    /**
     * 列表
     *
     * @param keyword   关键字
     * @param city      城市
     * @param pageUtils
     * @return
     */
    ResponseData topicList(String keyword, String city, PageUtils pageUtils);

    /**
     * 同一个用户的话题列表
     *
     * @param userId
     * @param pageUtils
     * @return
     */
    ResponseData oneUserList(Long userId, PageUtils pageUtils);

    /**
     * 排行榜
     *
     * @param userId
     * @param dayOrMonth
     * @param pageUtils
     * @return
     */
    ResponseData leaderBoard(Long userId,Integer dayOrMonth, PageUtils pageUtils);

    /**
     * 排名
     */
    void ranking();
}
