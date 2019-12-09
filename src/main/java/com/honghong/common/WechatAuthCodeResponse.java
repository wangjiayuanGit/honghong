package com.honghong.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangjy
 * @date 2019/9/6
 */
@Data
public class WechatAuthCodeResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String openid;

    @JsonProperty("session_key")
    private String sessionKey;

    /**
     * 请求失败错误码
     */
    private String errcode;

    /**
     * 请求失败错误信息
     */
    private String errmsg;

    /**
     * 会话有效期（以前微信会返回，现在未知）
     */
    @JsonProperty("expires_in")
    private Long expiresIn;


}

