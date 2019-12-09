package com.honghong.model.topic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.honghong.model.user.UserDO;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ：wangjy
 * @description ：点赞
 * @date ：2019/9/8 11:31
 */
@Data
@Entity
@Table(name = "tb_like")
public class LikeDO {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, columnDefinition = "bigint comment 'ID'")
    private Long id;

    @Column(name = "topic_id", nullable = false, columnDefinition = "bigint comment '话题ID'")
    private Long topicId;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "bigint comment '用户ID'")
    private UserDO user;

    @Column(name = "num", columnDefinition = "int comment '点赞数量'")
    private Integer num;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "created_at", columnDefinition = "datetime comment '创建时间'")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_at", columnDefinition = "datetime comment '修改时间'")
    private Date updatedAt;

    @Column(name = "state", columnDefinition = "int comment '状态'")
    private Integer state;
}
