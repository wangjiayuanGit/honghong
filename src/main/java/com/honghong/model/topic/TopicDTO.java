package com.honghong.model.topic;

import com.honghong.common.TopicType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author ：wangjy
 * @description ：话题DTO
 * @date ：2019/9/9 11:10
 */
@Data
public class TopicDTO {
    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID为空")
    private Long userId;
//    @ApiModelProperty(value = "标题")
//    @Size(max = 30, min = 0, message = "标题长度0~30")
    private String title;
    @ApiModelProperty(value = "内容")
    @Size(min = 1, max = 150, message = "内容长度1~150")
    private String content;
    @ApiModelProperty(value = "发布城市")
    private String city;
    @ApiModelProperty(value = "是否为真实数据")
    private Boolean isTrue;
}
