package com.wht.service;

import com.wht.mapper.ResourceMapper;
import com.wht.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wht
 * @date 2022/11/30 21:21
 */
@Service
public class ResourceServiceImpl {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ResourceMapper resourceMapper;

    @PostConstruct
    public void initData() {
        List<Map<String, String>> resourceRolesMap = resourceMapper.selectResourceMap();
        if(resourceRolesMap == null){
            throw new RuntimeException("资源未配置");
        }
        HashMap<String, String> map = new HashMap<>();
        for (Map<String, String> stringMap : resourceRolesMap) {
            map.put(stringMap.get("url"),stringMap.get("roles"));
        }
        redisUtil.hmset("auth:resourceRolesMap", map);
    }

}
