package com.honghong.repository;

import com.honghong.model.topic.TopicDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

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
     *
     * @param truth
     * @param start
     * @param end
     * @return
     */
    Integer countByTruthAndCreatedAtAfterAndCreatedAtBefore(Boolean truth, Date start, Date end);


    @Modifying
    @Query(value = "update tb_topic set ranking_of_the_day =0 where ranking_of_the_day !=0",nativeQuery = true)
    void updateRanking();

    List<TopicDO> findAllByUserId(Long userId);
}
