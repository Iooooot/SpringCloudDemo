package com.wht.serviceprovider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wht
 * @date 2022/11/24 21:08
 */
@RestController
public class OrderController {
    @Value("${server.port}")
    private String port;

    @GetMapping(value = "/echo/{str}")
    public String echo(@PathVariable(value = "str") String string) {
        return "Hello Nacos Discovery " + string + " 端口：" + port;
    }
}
