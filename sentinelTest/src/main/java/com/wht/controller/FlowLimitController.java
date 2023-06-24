package com.wht.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wht.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wht
 * @date 2022/11/20 19:52
 */
@RestController
public class FlowLimitController {

    @Autowired
    FeignService feignService;


    @GetMapping("/testA")
    // @SentinelResource(value = "testA",fallback = "handlerFallback")
    public String testA(){
        // return feignService.echo("11111111");
        return "111";
    }
    @GetMapping("/testB")
    public String testB() throws InterruptedException {
        Thread.sleep(1000);
        return "----testB";
    }
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "handlerException")
    public String testHotKey(@RequestParam(value = "v1",required = false) String v1,
                             @RequestParam(value = "v2",required = false) String v2){
        return "testHotKey" + v1 + v2;
    }

    public String handlerException(String v1, String v2, BlockException exception){
        return "系统限流暂时不可用..." + exception.getMessage();
    }
    public String handlerFallback(Throwable e){
        return "系统出现故障" + e.getMessage();
    }

}
