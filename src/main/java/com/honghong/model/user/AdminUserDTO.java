package com.honghong.model.user;

import com.honghong.common.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author ：wangjy
 * @description ：
 * @date ：2019/11/1 14:03
 */
@Data
public class AdminUserDTO {
    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    @NotEmpty(message = "密码不能为空")
    private String password;
    @ApiModelProperty("姓名")
    @NotEmpty(message = "姓名不能为空")
    private String name;
    @ApiModelProperty("电话号码")
    @NotEmpty(message = "电话号码不能为空")
    private String phone;
    @ApiModelProperty("性别")
    private Gender gender;
}
