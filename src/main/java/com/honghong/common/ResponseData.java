package com.honghong.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API返回信息默认数据结构
 *
 * @author wangjy
 * @date 2019/1/17
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据信息
     */
    private T data;

}
