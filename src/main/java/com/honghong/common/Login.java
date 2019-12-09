package com.honghong.common;

import java.lang.annotation.*;

/**
 * @author ：wangjy
 * @description ：登录校验
 * @date ：2019/12/8 22:35
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
}
