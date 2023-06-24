package com.wht.service;

/**
 * @author wht
 * @date 2022/11/20 20:00
 */
public class FeignServiceFallback implements FeignService{
    @Override
    public String echo(String string) {
        return "系统出现故障";
    }
}
