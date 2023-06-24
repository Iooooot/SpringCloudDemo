package com.wht.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @author wht
 * @date 2022/11/20 17:55
 */
public class CustomerBlockHandler {
    public static String handlerException(BlockException exception){
        return "系统被限流";
    }
}
