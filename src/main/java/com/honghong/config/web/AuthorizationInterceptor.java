package com.honghong.config.web;

import com.honghong.common.Login;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：wangjy
 * @description ：登录拦截器
 * @date ：2019/12/8 22:40
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Login annotation;
        // 如果处理对象是一个处理方法，则获取到方法上的注解
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
            // 否则直接放过拦截的请求
        } else {
            return true;
        }
        // 说明此方法没有Login注解
        if (annotation == null) {
            return true;
        }
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        // 如果还是没有token,则抛异常
        if (StringUtils.isBlank(token)) {
            throw new RuntimeException("请登录后再试！");
        }
        return false;
    }
}
