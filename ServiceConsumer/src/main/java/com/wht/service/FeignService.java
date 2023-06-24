package com.wht.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wht
 * @date 2022/11/15 20:31
 */
@Component
@FeignClient(value = "ServiceProvider")
public interface FeignService {
    @GetMapping(value = "/echo/{str}")
    public String echo(@PathVariable(value = "str") String string);
}
