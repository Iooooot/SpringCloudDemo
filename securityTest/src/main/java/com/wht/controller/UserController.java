package com.wht.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wht
 * @date 2022/12/12 19:29
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    TokenStore tokenStore;

    @PostMapping("/logout")
    public String removeToken(@RequestBody String access_token) {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(access_token.split("=")[1]);
        if (accessToken != null) {
            // 移除access_token
            tokenStore.removeAccessToken(accessToken);
            // 移除refresh_token
            if (accessToken.getRefreshToken() != null) {
                tokenStore.removeRefreshToken(accessToken.getRefreshToken());
            }
            return "注销成功！";
        }
        return "注销失败！";
    }


}
