package com.honghong.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.honghong.common.Gender;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ：wangjy
 * @description ：后台管理员用户
 * @date ：2019/11/1 13:51
 */
@Data
@Entity
@Table(name = "tb_admin_user")
public class AdminUserDO {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, columnDefinition = "bigint comment 'id'")
    private Long id;
    @Column(name = "username", columnDefinition = "varchar(30) comment '用户名'")
    private String username;
    @Column(name = "password", columnDefinition = "varchar(50) comment '密码'")
    private String password;
    @Column(name = "name", columnDefinition = "varchar(20) comment '姓名'")
    private String name;
    @Column(name = "phone", columnDefinition = "varchar(11) comment '电话'")
    private String phone;
    @Column(name = "gender", columnDefinition = "int comment '性别'")
    private Gender gender;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "created_at", columnDefinition = "datetime comment '创建时间'")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_at", columnDefinition = "datetime comment '修改时间'")
    private Date updatedAt;
    @Column(name = "state", columnDefinition = "int comment '状态'")
    private Integer state;

    public void addAdminUser(AdminUserDTO adminUserDTO) {
        Date date = new Date();
        this.username = adminUserDTO.getUsername();
        this.password = adminUserDTO.getPassword();
        this.name = adminUserDTO.getName();
        this.phone = adminUserDTO.getPhone();
        this.gender = adminUserDTO.getGender();
        this.createdAt = date;
        this.updatedAt = date;
        this.state = 0;
    }

    public void updateAdminUser(AdminUserDTO adminUserDTO) {
        Date date = new Date();
        this.username = adminUserDTO.getUsername();
        this.password = adminUserDTO.getPassword();
        this.name = adminUserDTO.getName();
        this.gender = adminUserDTO.getGender();
        this.updatedAt = date;
    }
}
