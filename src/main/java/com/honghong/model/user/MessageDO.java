package com.honghong.model.user;

import lombok.Data;

/**
 * @author ：wangjy
 * @description ：Message
 * @date ：2019/9/3 16:12
 */
@Data
public class MessageDO {
    private Integer id;
    private Integer likeNum;
    transient private Integer weight;

    public MessageDO(Integer id, Integer likeNum) {
        this.id = id;
        this.likeNum = likeNum;
    }
}
