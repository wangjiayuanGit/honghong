package com.honghong.config.wechat;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ：wangjy
 * @description ：
 * @date ：2019/9/5 23:00
 */
@Component
@Data
public class WechatAuthProperties {

    @Value("${auth.wechat.sessionHost}")
    private String sessionHost;

    @Value("${auth.wechat.appId}")
    private String appId;

    @Value("${auth.wechat.secret}")
    private String secret;

    @Value("${auth.wechat.grantType}")
    private String grantType;

}