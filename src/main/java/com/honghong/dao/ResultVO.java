package com.honghong.dao;

import lombok.Data;

/**
 * @author ：wangjy
 * @description ：结果集
 * @date ：2019/12/2 16:04
 */
@Data
public class ResultVO {
    private Integer likeSum;
    private Integer commentSum;
    private Boolean truth;
}
