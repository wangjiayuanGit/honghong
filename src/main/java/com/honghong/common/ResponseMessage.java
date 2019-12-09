package com.honghong.common;

/**
 * @author wangjy
 * @date 2019/1/17
 */

public class ResponseMessage {
    /**
     * 服务器处理成功
     */

    public static final String SUCCESS = "成功";

    /**
     * 无内容， 服务器成功处理了请求,但没有返回null
     */
    public static final String DATA_NULL = "请求数据为空";

    /**
     * 请求的安全验证未通过
     */
    public static final String REQUEST_VALIDATE = "验签未通过";

    /**
     * token有误
     */
    public static final String TOKEN_ERROR = "token有误";
    /**
     * 未授权，请求要求身份验证
     */
    public static final String UNAUTHORIZED = "未授权";
    /**
     * 请求参数异常
     */
    public static final String PARAM_ERROR = "请求参数异常";

    /**
     * 服务器内部错误
     * 例如自身代码逻辑错误
     */
    public static final String SERVER_INTERNAL_ERROR = "服务器内部错误";
    /**
     * 服务器正忙，请稍后再试
     */
    public static final String SERVER_BUSY = "服务器正忙，请稍后再试";
}
