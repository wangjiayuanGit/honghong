package com.honghong.config.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author ：wangjy
 * @description ：拦截器
 * @date ：2019/11/20 14:06
 */
@Component
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResourceInterceptor resourceInterceptor;
//    @Autowired
//    private AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("执行InterceptorRegistry");
        registry.addInterceptor(resourceInterceptor);
//        registry.addInterceptor(authorizationInterceptor);
    }
}
