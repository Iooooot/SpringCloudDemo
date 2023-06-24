package com.wht.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (TAccount)表实体类
 *
 * @author makejava
 * @since 2022-11-21 21:24:21
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_account")
public class TAccount  {
    @TableId
    private Long id;

    //用户id
    private Long userId;
    //总额度
    private Double total;
    //已用余额
    private Double used;
    //剩余可用额度
    private Double residue;



}
