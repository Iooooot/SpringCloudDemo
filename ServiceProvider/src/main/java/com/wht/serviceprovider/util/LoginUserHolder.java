package com.wht.serviceprovider.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wht
 * 获取登录用户信息
 * @date 2022/12/7 21:44
 */
@Component
public class LoginUserHolder {

    public String getCurrentUser(){
        //从Header中获取用户信息
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getHeader("Authorization");
    }
}
