package com.honghong.model.topic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author ：wangjy
 * @description ：
 * @date ：2020/2/24 21:36
 */
@Data
public class LikeVO {
    private BigInteger topicId;
    private BigInteger userId;
    private BigDecimal likeNum;
    private Integer likeSum;
    private Integer commentSum;
    private String nickname;
    private String content;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date creatAt;
    private String headImg;
}
