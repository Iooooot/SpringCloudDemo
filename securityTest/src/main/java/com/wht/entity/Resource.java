package com.wht.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 后台资源表(Resource)表实体类
 *
 * @author makejava
 * @since 2022-11-30 21:28:18
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_resource")
public class Resource  {
    @TableId
    private Long id;

    //创建时间
    private Date createTime;
    //资源名称
    private String name;
    //资源URL
    private String url;
    //描述
    private String description;
    //资源分类ID
    private Long categoryId;



}
