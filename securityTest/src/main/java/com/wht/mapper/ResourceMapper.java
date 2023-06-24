package com.wht.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wht.entity.Resource;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


/**
 * 后台资源表(Resource)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-30 21:28:18
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {
    /**
     * 查询出资源与角色的对应map
     * @return
     */
    @MapKey("url")
    List<Map<String, String>> selectResourceMap();
}

