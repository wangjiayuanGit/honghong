package com.honghong.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.honghong.common.ResponseData;
import com.honghong.common.WechatAuthCodeResponse;
import com.honghong.config.wechat.AppContext;
import com.honghong.config.wechat.WechatAuthProperties;
import com.honghong.model.user.AccountDTO;
import com.honghong.model.user.UserDO;
import com.honghong.repository.UserRepository;
import com.honghong.service.WechatService;
import com.honghong.util.ResultUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ：wangjy
 * @description ：
 * @date ：2019/9/6 13:57
 */
@Service
public class WechatServiceImpl implements WechatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * 服务器第三方session有效时间，单位秒, 默认1天
     */
    private static final Long EXPIRES = 86400L;

    private RestTemplate wxAuthRestTemplate = new RestTemplate();

    @Autowired
    private WechatAuthProperties wechatAuthProperties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public ResponseData getUserInfo(String code) {
        WechatAuthCodeResponse wechatAuthCodeResponse = getWxSession(code);
        String openId = wechatAuthCodeResponse.getOpenid();
        UserDO userDO = userRepository.findByWechatOpenid(openId);
        return ResultUtils.success(userDO);
    }

    public ResponseData weChatLogin(AccountDTO accountDto) {
        WechatAuthCodeResponse response = getWxSession(accountDto.getCode());
        Map<String, Object> map = new HashMap<>();
        String wxOpenId = response.getOpenid();
        String wxSessionKey = response.getSessionKey();
        UserDO userDO = new UserDO();
        Date date = new Date();
        //TODO 添加用户信息
        userDO.setWechatOpenid(wxOpenId);
        userDO.setNickname(accountDto.getNickName());
        userDO.setHeadImg(accountDto.getAvatarUrl());
        userDO.setGender(accountDto.getGender());
        userDO.setCountry(accountDto.getCountry());
        userDO.setProvince(accountDto.getProvince());
        userDO.setCity(accountDto.getCity());
        userDO.setState(0);
        userDO.setCreatedAt(date);
        userDO.setUpdatedAt(date);
        userDO.setLastLoginTime(date);
        map.put("user", loginOrRegisterUser(userDO));
        Long expires = response.getExpiresIn();
        String token = create3rdSession(wxOpenId, wxSessionKey, expires);
        map.put("thirdSession", token);
        return ResultUtils.success(map);
    }

    /**
     * 调用 微信 jscode2session 接口 获取授权信息
     *
     * @param code
     * @return
     */
    private WechatAuthCodeResponse getWxSession(String code) {
        LOGGER.info(code);
        String urlString = "?appid={appid}&secret={srcret}&js_code={code}&grant_type={grantType}";
        String response = wxAuthRestTemplate.getForObject(wechatAuthProperties.getSessionHost() + urlString,
                String.class,
                wechatAuthProperties.getAppId(),
                wechatAuthProperties.getSecret(),
                code,
                wechatAuthProperties.getGrantType());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectReader reader = objectMapper.readerFor(WechatAuthCodeResponse.class);
        WechatAuthCodeResponse res;
        try {
            res = reader.readValue(response);
        } catch (IOException e) {
            res = null;
            LOGGER.error("反序列化失败", e);
        }
        LOGGER.info(response);
        if (null == res) {
            throw new RuntimeException("调用微信接口失败");
        }
        if (res.getErrcode() != null) {
            throw new RuntimeException(res.getErrmsg());
        }
        res.setExpiresIn(res.getExpiresIn() != null ? res.getExpiresIn() : EXPIRES);
        return res;
    }

    /**
     * 将token 存入redis
     *
     * @param wxOpenId
     * @param wxSessionKey
     * @param expires
     * @return
     */
    private String create3rdSession(String wxOpenId, String wxSessionKey, Long expires) {
        String thirdSessionKey = RandomStringUtils.randomAlphanumeric(64);
        StringBuffer sb = new StringBuffer();
        sb.append(wxSessionKey).append("#").append(wxOpenId);

        stringRedisTemplate.opsForValue().set(thirdSessionKey, sb.toString(), expires, TimeUnit.SECONDS);
        return thirdSessionKey;
    }

    @Override
    public UserDO loginOrRegisterUser(UserDO userDO) {
        UserDO userDO1 = userRepository.findByWechatOpenid(userDO.getWechatOpenid());
        if (null == userDO1) {
            return userRepository.save(userDO);
        } else {
            userDO1.setLastLoginTime(new Date());
            userDO1.setNickname(userDO.getNickname());
            userDO1.setCountry(userDO.getCountry());
            userDO1.setProvince(userDO.getProvince());
            userDO1.setCity(userDO.getCity());
            userDO1.setHeadImg(userDO.getHeadImg());
            return userRepository.saveAndFlush(userDO1);
        }
    }
}


