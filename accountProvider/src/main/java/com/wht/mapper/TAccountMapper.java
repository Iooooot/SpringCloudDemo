package com.wht.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wht.entity.TAccount;
import org.apache.ibatis.annotations.Mapper;


/**
 * (TAccount)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-21 21:24:18
 */
@Mapper
public interface TAccountMapper extends BaseMapper<TAccount> {

}

