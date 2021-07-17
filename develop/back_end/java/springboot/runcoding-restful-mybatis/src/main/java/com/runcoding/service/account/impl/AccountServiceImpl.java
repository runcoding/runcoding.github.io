package com.runcoding.service.account.impl;

import com.runcoding.dao.account.AccountMapper;
import com.runcoding.model.po.account.AccountPo;
import com.runcoding.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author runcoding
 * @Date 2018-01-02 17:22:57
 */
@Service
public class AccountServiceImpl implements AccountService {

    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:22:57
     */
    @Autowired
    private AccountMapper accountMapper;

    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:22:57
     */
    @Override
    public AccountPo select(Long id) {
        return accountMapper.select(id);
    }

    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:22:57
     */
    @Override
    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public int insert(AccountPo po) {
        return accountMapper.insert(po);
    }

    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:22:57
     */
    @Override
    public int update(AccountPo po) {
        return accountMapper.update(po);
    }

    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:22:57
     */
    @Override
    public int delete(AccountPo po) {
        return accountMapper.delete(po);
    }
}