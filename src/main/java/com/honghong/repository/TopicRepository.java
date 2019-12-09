package com.honghong.repository;

import com.honghong.model.topic.TopicDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

/**
 * @author ：wangjy
 * @description ：
 * @date ：2019/9/9 16:32
 */
public interface TopicRepository extends JpaRepository<TopicDO, Long>, JpaSpecificationExecutor<TopicDO> {
    /**
     * 根据用户ID查询
     *
     * @param userId
     * @return
     */
    Page<TopicDO> findAllByUserId(Long userId, Pageable pageable);

    /**
     * 真实数据的数量
     * @param truth
     * @param start
     * @param end
     * @return
     */
    Integer countByTruthAndCreatedAtAfterAndCreatedAtBefore(Boolean truth, Date start,Date end);

}
