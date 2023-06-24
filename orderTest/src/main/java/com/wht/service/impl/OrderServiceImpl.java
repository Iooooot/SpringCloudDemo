package com.wht.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wht.entity.Order;
import com.wht.mapper.OrderMapper;
import com.wht.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * (Order)表服务实现类
 *
 * @author makejava
 * @since 2022-11-21 20:21:17
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
