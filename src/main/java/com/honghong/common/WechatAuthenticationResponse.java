package com.honghong.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WechatAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    @JsonProperty("access_token")
    private final String accessToken;

    public WechatAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }


    public String getAccess_token() {
        return this.accessToken;
    }

}
 