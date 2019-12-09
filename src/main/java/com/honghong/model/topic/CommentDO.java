package com.honghong.model.topic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.honghong.common.CommentType;
import com.honghong.model.user.UserDO;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ：wangjy
 * @description ：评论
 * @date ：2019/9/23 16:32
 */
@Data
@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
@Table(name = "tb_comment")
public class CommentDO {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, columnDefinition = "bigint comment 'ID'")
    private Long id;

    @Column(name = "type", columnDefinition = "int comment '评论主体类型'")
    private CommentType type;

    @Column(name = "topic_id", columnDefinition = "bigint comment '话题ID'")
    private Long topicId;

    @Column(name = "owner_id", nullable = false, columnDefinition = "bigint comment '被评论者的id(评论对象ID)'")
    private Long ownerId;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "from_id", columnDefinition = "bigint comment '评论者id'")
    private UserDO user;

    @Column(name = "content", columnDefinition = "varchar(255)comment '评论内容'")
    private String content;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "created_at", columnDefinition = "datetime comment '创建时间'")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_at", columnDefinition = "datetime comment '修改时间'")
    private Date updatedAt;

    @Column(name = "state", columnDefinition = "int comment '状态'")
    private Integer state;


}
