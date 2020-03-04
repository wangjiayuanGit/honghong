package com.honghong.service;

import com.honghong.common.ResponseData;
import com.honghong.util.PageUtils;

/**
 * @author ：wangjy
 * @description ：点赞
 * @date ：2019/9/19 17:54
 */
public interface LikeService {
    /**
     * 点赞
     *
     * @param topicId
     * @param userId
     * @param num
     * @return
     */
    ResponseData like(Long topicId, Long userId, Integer num);

    /**
     * 我的点赞列表
     *
     * @param userId
     * @param pageUtils
     * @return
     */
    ResponseData likeList(Long userId, PageUtils pageUtils);
}
