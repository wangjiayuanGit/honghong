package com.honghong.util;

import com.honghong.common.MyPage;
import com.honghong.common.ResponseCode;
import com.honghong.common.ResponseData;
import com.honghong.common.ResponseMessage;
import org.springframework.data.domain.Page;

/**
 * @Author: kent
 * @Date: 2018/10/30
 */
public class ResultUtils {
    /**
     * 成功
     *
     * @return
     */
    public static ResponseData success() {
        return new ResponseData<>(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, null);
    }

    /**
     * 成功并且有返回对象
     *
     * @param data
     * @return
     */
    public static ResponseData success(Object data) {
        return new ResponseData<>(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    public static ResponseData success(Page page) {
        return new ResponseData<>(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, new MyPage(page));
    }

    /**
     * 数据为空
     *
     * @return
     */
    public static ResponseData dataNull() {
        return new ResponseData<>(ResponseCode.PARAM_ERROR, ResponseMessage.DATA_NULL, null);
    }

    /**
     * 请求的安全验证未通过
     *
     * @return
     */
    public static ResponseData requestValidate() {
        return new ResponseData<>(ResponseCode.REQUEST_VALIDATE, ResponseMessage.REQUEST_VALIDATE, null);
    }

    /**
     * token验证有误
     *
     * @return
     */
    public static ResponseData tokenError() {
        return new ResponseData<>(ResponseCode.TOKEN_ERROR, ResponseMessage.TOKEN_ERROR, null);
    }

    /**
     * 请求参数异常
     *
     * @return
     */
    public static ResponseData paramError() {
        return new ResponseData<>(ResponseCode.PARAM_ERROR, ResponseMessage.PARAM_ERROR, null);
    }

    /**
     * 触发运行时异常
     *
     * @return
     */
    public static ResponseData customerRuntimeException(String msg) {
        return new ResponseData<>(ResponseCode.RUNTIME_ERROR, msg, null);
    }

    /**
     * 未授权
     *
     * @return
     */
    public static ResponseData unauthorized() {
        return new ResponseData<String>(ResponseCode.UNAUTHORIZED, ResponseMessage.UNAUTHORIZED, null);
    }

    /**
     * 服务器内部错误
     *
     * @return
     */
    public static ResponseData internalError() {
        return new ResponseData<>(ResponseCode.SERVER_INTERNAL_ERROR, ResponseMessage.SERVER_INTERNAL_ERROR, null);
    }

    /**
     * 服务器正忙，请稍后再试
     *
     * @return
     */
    public static ResponseData serverBusy() {
        return new ResponseData<>(ResponseCode.SERVER_BUSY, ResponseMessage.SERVER_BUSY, null);
    }

    /**
     * 通用
     *
     * @return
     */
    public static ResponseData common(Integer code, String msg) {
        return new ResponseData<>(code, msg, null);
    }

    /**
     * 通用
     *
     * @return
     */
    public static ResponseData common(Integer code) {
        return new ResponseData<>(code, "错误代码：" + code, null);
    }


}
