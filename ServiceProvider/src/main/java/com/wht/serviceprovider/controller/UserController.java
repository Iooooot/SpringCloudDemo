package com.wht.serviceprovider.controller;

import com.wht.serviceprovider.util.LoginUserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wht
 * @date 2022/12/7 21:43
 */
@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private LoginUserHolder loginUserHolder;

    @GetMapping("/currentUser")
    public String currentUser() {
        return loginUserHolder.getCurrentUser();
    }

}
