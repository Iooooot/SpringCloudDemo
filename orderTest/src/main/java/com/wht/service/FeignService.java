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
@FeignClient(value = "accountProvider")
public interface FeignService {
    @GetMapping(value = "/account/{userId}/{money}")
    void updateAccount(@PathVariable(value = "userId") String userId,@PathVariable(value = "money") Double money);
}
