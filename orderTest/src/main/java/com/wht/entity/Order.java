package com.wht.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * (Order)表实体类
 *
 * @author makejava
 * @since 2022-11-21 20:21:16
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_order")
public class Order  {
    @TableId
    private Long id;

    //用户id
    private Long userId;
    //产品id
    private Long productId;
    //数量
    private Integer count;
    //金额
    private Double money;
    //订单状态:0:创建中；1:已完结
    private Integer status;



}
