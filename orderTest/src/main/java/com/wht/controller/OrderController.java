package com.wht.controller;

import com.wht.entity.Order;
import com.wht.service.FeignService;
import com.wht.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wht
 * @date 2022/11/21 20:21
 */
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    FeignService feignService;

    @GlobalTransactional(rollbackFor = Exception.class)
    @GetMapping("setOrder/{userId}/{commodityId}")
    public String setOrder(@PathVariable("userId") String userId,@PathVariable("commodityId") String commodityId){
        // 创建订单
        Order order = new Order();
        order.setId(4L);
        order.setCount(1);
        order.setMoney(3.5);
        order.setStatus(0);
        order.setUserId(Long.parseLong(userId));
        order.setProductId(Long.parseLong(commodityId));
        orderService.save(order);
        // 减少用户余额
        feignService.updateAccount(userId,order.getMoney());
        int i = 1/0;
        // 更新订单状态
        Order order2 = new Order();
        order2.setId(1L);
        order2.setStatus(1);
        orderService.updateById(order2);
        return "下单成功！";
    }
}
