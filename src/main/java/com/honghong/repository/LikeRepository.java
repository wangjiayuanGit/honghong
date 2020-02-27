package com.honghong.repository;

import com.honghong.model.topic.LikeDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author ：wangjy
 * @description ：点赞
 * @date ：2019/9/19 17:57
 */
public interface LikeRepository extends JpaRepository<LikeDO, Long> {
    /**
     * 根据话题ID查询点赞列表
     *
     * @param userId
     * @return
     */
    @Query(value = "SELECT T.id AS topicId, T.content AS content, T.update_at AS updateAt," +
            "T.created_at AS creatAt, T.like_sum AS likeSum,T.comment_sum AS commentSum," +
            "U.id AS userId, U.head_img AS headImg, U.nickname AS nickname, SUM( L.num ) AS likeNum  " +
            "FROM tb_like L LEFT JOIN tb_topic T ON T.id = L.topic_id LEFT JOIN tb_user U ON U.id = L.user_id  " +
            "WHERE T.id IN ( SELECT id FROM tb_topic WHERE user_id = ?1 ) GROUP BY T.id, U.id ORDER BY T.update_at DESC", nativeQuery = true)
    List<Map<String, Object>> findAllByUserId(Long userId);


}
