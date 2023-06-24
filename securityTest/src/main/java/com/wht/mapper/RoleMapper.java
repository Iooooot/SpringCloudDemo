package com.wht.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wht.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-30 21:11:10
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据id查询用户角色
     * @param id
     * @return
     */
    List<String> selectRolesByUserId(@Param("userid") Long id);
}

