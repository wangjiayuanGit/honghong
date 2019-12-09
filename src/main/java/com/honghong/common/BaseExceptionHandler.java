package com.honghong.common;

import com.honghong.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

/**
 * 全局异常处理
 *
 * @author wangjy
 * @date 2019/12/08
 */
@ResponseBody
@ControllerAdvice
public class BaseExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @ExceptionHandler(Exception.class)
    public ResponseData exception(Exception e) {
        logger.error(e.getMessage(), e);
        return ResultUtils.serverBusy();
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseData runtimeException(RuntimeException re) {
        logger.error(re.getMessage(), re);
        return ResultUtils.customerRuntimeException(re.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseData dataAccessException(DataAccessException dae) {
        logger.error(dae.getMessage(), dae);
        return ResultUtils.common(ResponseCode.PARAM_ERROR, "数据异常");
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResponseData unsupportedEncodingException(UnsupportedEncodingException uee) {
        logger.error(uee.getMessage(), uee);
        return ResultUtils.common(ResponseCode.PARAM_ERROR, "数据异常");
    }
}
