package com.honghong.model.topic;

import lombok.Data;

import java.util.List;

/**
 * @author ：wangjy
 * @description ：
 * @date ：2020/2/26 22:04
 */
@Data
public class CommentResult {
    private TopicDO topicDO;
    private List<CommentDO> commentDOS;

    public CommentResult(List<CommentDO> commentDOS, TopicDO topicDO) {
        this.commentDOS = commentDOS;
        this.topicDO = topicDO;
    }

}
