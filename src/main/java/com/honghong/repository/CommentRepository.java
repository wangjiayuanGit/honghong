package com.honghong.repository;

import com.honghong.common.CommentType;
import com.honghong.model.topic.CommentDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：wangjy
 * @description ：评论
 * @date ：2019/9/23 17:01
 */
public interface CommentRepository extends JpaRepository<CommentDO, Long> {
    /**
     * 评论列表
     *
     * @param ownerId  评论对象ID
     * @param type     评论类型（文章评论/子评论/回复）
     * @param state    状态
     * @param pageable
     * @return
     */
    Page<CommentDO> findAllByOwnerIdAndTypeAndStateIsNot(Long ownerId, CommentType type, Integer state, Pageable pageable);

    Page<CommentDO> findAllByTopicIdAndStateIsNot(Long topicId, Integer state, Pageable pageable);
}
