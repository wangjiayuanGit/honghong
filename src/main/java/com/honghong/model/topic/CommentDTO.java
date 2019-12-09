package com.honghong.model.topic;

import com.honghong.common.CommentType;
import com.honghong.model.user.UserDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * @author ：wangjy
 * @description ：评论传输实体类
 * @date ：2019/9/23 16:57
 */
@Data
public class CommentDTO {
    @NotNull(message = "评论主体类型不能为空")
    @ApiModelProperty("评论主体类型")
    private CommentType type;

    @ApiModelProperty("被评论者的id")
    @NotNull(message = "被评论者的id不能为空")
    private Long ownerId;


    @ApiModelProperty("话题的id")
    @NotNull(message = "话题ID不能为空")
    private Long topicId;

    @ApiModelProperty("评论者id")
    @NotNull(message = "评论者id不能为空")
    private Long userId;

    @ApiModelProperty("评论内容")
    private String content;
}
