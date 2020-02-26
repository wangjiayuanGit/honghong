package com.honghong.model.topic;

import lombok.Data;

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

    public LikeDTO(List<LikeVO> list) {
        this.topicId = Long.valueOf(list.get(0).getTopicId().toString());
        this.content = list.get(0).getContent();
        this.likeVOS = list;
    }
}
