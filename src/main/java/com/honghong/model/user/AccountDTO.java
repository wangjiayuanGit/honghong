package com.honghong.model.user;

import com.honghong.common.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AccountDTO {
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("头像地址")
    private String avatarUrl;
    @ApiModelProperty("性别")
    private Gender gender;
    @ApiModelProperty("国家")
    private String country;
    @ApiModelProperty("省")
    private String province;
    @ApiModelProperty("市")
    private String city;
    @ApiModelProperty("显示 country，province，city 所用的语言")
    private String language;
    @NotEmpty(message = "code 不能为空")
    private String code;

}
