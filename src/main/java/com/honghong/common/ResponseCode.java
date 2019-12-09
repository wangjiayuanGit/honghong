package com.honghong.common;

/**
 * API接口返回码
 *
 * @Author: kent
 * @Date: 2019/9/3
 */
public class ResponseCode {

    /**
     * 服务器处理成功
     */
    public static final Integer SUCCESS = 200;
    /**
     * 无内容， 服务器成功处理了请求,但没有返回null
     */
    public static final Integer DATA_NULL = 204;


    /**
     * 用户未登录
     */
    public static final Integer TOKEN_UNLOGIN = 300;
    /**
     * session失效
     */
    public static final Integer TOKEN_INVALID = 301;
    /**
     * token非法
     */
    public static final Integer TOKEN_ILLEGAL = 302;
    /**
     * 传入TOKEN与当前回话存储token不匹配
     */
    public static final Integer TOKEN_MISMATCH = 303;


    //4xx 请求错误。这些状态代码表示请求可能出错,妨碍了服务器的处理。例如请求参数错误

    /**
     * 请求的安全验证未通过
     */
    public static final Integer REQUEST_VALIDATE = 400;
    /**
     * 未授权，请求要求身份验证
     */
    public static final Integer UNAUTHORIZED = 401;
    /**
     * 触发运行时异常
     */
    public static final Integer RUNTIME_ERROR = 402;
    /**
     * token有误
     */
    public static final Integer TOKEN_ERROR = 403;
    /**
     * 请求参数异常
     */
    public static final Integer PARAM_ERROR = 406;
    /**
     * 验证码失效
     */
    public static final Integer INVALID = 407;
    /**
     * 验证码错误
     */
    public static final Integer ERROR_VERIFY_CODE = 408;


    //5xx 服务器错误。表示服务器在尝试处理请求时发生内部错误。 这些错误可能是服务器本身的错误,而不是请求出错。
    /**
     * 服务器内部错误
     * 例如自身代码逻辑错误
     */
    public static final Integer SERVER_INTERNAL_ERROR = 500;
    /**
     * 服务器正忙，请稍后再试
     */
    public static final Integer SERVER_BUSY = 503;


}
