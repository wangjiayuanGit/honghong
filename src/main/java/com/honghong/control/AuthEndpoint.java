package com.honghong.control;

import com.honghong.common.ResponseData;
import com.honghong.model.user.AccountDTO;
import com.honghong.service.impl.WechatServiceImpl;
import com.honghong.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Api(description = "微信授权")
public class AuthEndpoint {
    @Autowired
    private WechatServiceImpl wechatService;

    /**
     * 授权登录
     *
     * @param accountDto
     * @return
     * @throws AuthenticationException
     */
    @PostMapping("/auth")
    @ApiOperation("微信授权登录")
    public ResponseData createAuthenticationToken(@Valid AccountDTO accountDto) throws AuthenticationException {
        return ResultUtils.success(wechatService.weChatLogin(accountDto));
    }
}
