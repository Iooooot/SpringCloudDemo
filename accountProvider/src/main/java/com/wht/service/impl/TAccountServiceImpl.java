package com.wht.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wht.entity.TAccount;
import com.wht.mapper.TAccountMapper;
import com.wht.service.TAccountService;
import org.springframework.stereotype.Service;

/**
 * (TAccount)表服务实现类
 *
 * @author makejava
 * @since 2022-11-21 21:24:22
 */
@Service("tAccountService")
public class TAccountServiceImpl extends ServiceImpl<TAccountMapper, TAccount> implements TAccountService {

}
