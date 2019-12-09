package com.honghong.service;

import com.honghong.model.user.UserDO;

public interface WechatService {
    /**
     * 授权登录
     * @param userDO
     * @return
     */
    UserDO loginOrRegisterUser(UserDO userDO);
}
