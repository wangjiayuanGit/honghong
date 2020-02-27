package com.honghong.model.topic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.honghong.common.TopicType;
import com.honghong.model.user.UserDO;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ：wangjy
 * @description ：话题
 * @date ：2019/9/8 10:57
 */
@Data
@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
@Table(name = "tb_topic")
public class TopicDO {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, columnDefinition = "bigint comment 'ID'")
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "bigint comment '用户ID'")
    private UserDO user;

//    @Column(name = "title", columnDefinition = "varchar(30) comment '标题'")
//    private String title;

    @Column(name = "content", columnDefinition = "varchar(150) comment '内容'")
    private String content;

    @Column(name = "type", columnDefinition = "int comment '话题分类'")
    private TopicType type;

    @Column(name = "city", columnDefinition = "varchar(15) comment '发布城市'")
    private String city;

    @Column(name = "truth", columnDefinition = "boolean comment '是否真实数据'")
    private Boolean truth;

    @Column(name = "like_sum", columnDefinition = "int comment '点赞数量'")
    private Integer likeSum;

    @Column(name = "comment_sum", columnDefinition = "int comment '评论数量'")
    private Integer commentSum;

    @Column(name = "ranking_or_the_day", columnDefinition = "int comment '当天点赞排名'")
    private Integer rankingOfTheDay;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "created_at", columnDefinition = "datetime comment '创建时间'")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_at", columnDefinition = "datetime comment '修改时间'")
    private Date updatedAt;

    @Column(name = "state", columnDefinition = "int comment '状态'")
    private Integer state;
    @Column(name = "nickname", columnDefinition = "varchar(30) comment '微信昵称'")
    private String nickname;
    @Column(name = "like_user_num", columnDefinition = "int comment '点赞人数'")
    private Integer likeUserNum;
    @Column(name = "last_like_user", columnDefinition = "varchar (50) comment '最后点赞人的昵称'")
    private String lastLikeUser;

    transient private Integer weight;
    transient private Double efficiency;
    transient private Integer ranking;


    public void addTopic(TopicDTO topicDTO) {
//        this.title = topicDTO.getTitle();
        this.content = topicDTO.getContent();
        this.city = topicDTO.getCity();
        this.truth = topicDTO.getIsTrue();
        this.likeSum = 0;
        this.commentSum = 0;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.state = 0;
        this.nickname = this.user.getNickname();
        this.likeUserNum = 0;
    }


}
