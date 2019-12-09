package com.honghong.repository;

import com.honghong.model.topic.LikeDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ：wangjy
 * @description ：点赞
 * @date ：2019/9/19 17:57
 */
public interface LikeRepository extends JpaRepository<LikeDO, Long> {
    /**
     * 根据话题ID查询点赞列表
     *
     * @param topicId
     * @return
     */
    List<LikeDO> findAllByTopicId(Long topicId);
}
