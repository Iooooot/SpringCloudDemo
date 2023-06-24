package com.wht.controller;

import com.wht.entity.TAccount;
import com.wht.mapper.TAccountMapper;
import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wht
 * @date 2022/11/21 21:24
 */
@RestController
public class AccountController {
    @Autowired
    TAccountMapper accountMapper;

    @GetMapping(value = "/account/{userId}/{money}")
    void updateAccount(@PathVariable(value = "userId") String userId,@PathVariable(value = "money") Double money){
        TAccount account = accountMapper.selectById(Long.parseLong(userId));
        String xid = RootContext.getXID();
        if(account == null){
            throw new RuntimeException("账号错误");
        }
        account.setUsed(account.getUsed() + money);
        if(account.getResidue() - money < 0){
            throw new RuntimeException("金额异常");
        }
        account.setResidue(account.getResidue() - money);
        accountMapper.updateById(account);
    }
}
