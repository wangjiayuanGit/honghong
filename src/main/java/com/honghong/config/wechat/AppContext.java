package com.honghong.config.wechat;

/**
 * @author ：wangjy
 * @description ：存储一次HTTP请求会话 wechat_openid 从而找找到对应的用户
 * @date ：2019/9/5 22:57
 */
public class AppContext implements AutoCloseable {

    private static final ThreadLocal<String> CURRENT_CONSUMER_WECHAT_OPENID = new ThreadLocal<>();

    public AppContext(String wechatOpenid) {
        CURRENT_CONSUMER_WECHAT_OPENID.set(wechatOpenid);
    }

    @Override
    public void close() {
        CURRENT_CONSUMER_WECHAT_OPENID.remove();
    }

    public static String getCurrentUserWechatOpenId() {
        return CURRENT_CONSUMER_WECHAT_OPENID.get();
    }

}