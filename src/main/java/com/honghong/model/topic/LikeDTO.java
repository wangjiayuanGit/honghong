package com.honghong.model.topic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author ：wangjy
 * @description ：
 * @date ：2020/2/26 10:11
 */
@Data
public class LikeDTO {
    private List<LikeVO> likeVOS;
    private Long topicId;
    private String content;
    private Integer likeSum;
    private Integer commentSum;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date creatAt;

    public LikeDTO(List<LikeVO> list) {
        this.topicId = Long.valueOf(list.get(0).getTopicId().toString());
        this.likeSum = list.get(0).getLikeSum();
        this.commentSum = list.get(0).getCommentSum();
        this.content = list.get(0).getContent();
        this.creatAt = list.get(0).getCreatAt();
        this.likeVOS = list;

    }
}
