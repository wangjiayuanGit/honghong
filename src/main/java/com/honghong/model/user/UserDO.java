package com.honghong.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.honghong.common.Gender;
import com.honghong.model.topic.TopicDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author ：wangjy
 * @description ：
 * @date ：2019/9/6 10:45
 */
@Data
@Entity
@Table(name = "tb_user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class UserDO {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, columnDefinition = "bigint comment 'id'")
    private Long id;
    @Column(name = "wechat_openid", columnDefinition = "varchar(30)comment 'openid'")
    private String wechatOpenid;
    @Column(name = "nickname", columnDefinition = "varchar(30) comment '昵称'")
    private String nickname;
    @Column(name = "head_img", columnDefinition = "varchar(255) comment '头像'")
    private String headImg;
    @Column(name = "gender", columnDefinition = "int comment '性别'")
    private Gender gender;
    @Column(name = "country", columnDefinition = "varchar(20) comment '国家'")
    private String country;
    @Column(name = "province", columnDefinition = "varchar(20) comment '省份'")
    private String province;
    @Column(name = "city", columnDefinition = "varchar(20) comment '城市'")
    private String city;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "last_login_time", columnDefinition = "datetime comment '上次登录时间'")
    private Date lastLoginTime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "created_at", columnDefinition = "datetime comment '创建时间'")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_at", columnDefinition = "datetime comment '修改时间'")
    private Date updatedAt;
    @Column(name = "state", columnDefinition = "int comment '状态'")
    private Integer state;


//    @JsonIgnore
//    @OneToMany(mappedBy = "parent", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
//    List<TopicDO> topicDOS;

    public void fakeData(String nickname, String headImg, String city,Date createdAt) {
        this.nickname = nickname;
        this.headImg = headImg;
        this.city = city;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
        this.state = 0;
    }
}
