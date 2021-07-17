package com.runcoding.service.account;

import com.runcoding.model.po.account.AccountPo;


/**
 * @author runcoding
 * @Date 2018-01-02 17:22:57
 */
public interface AccountService {
    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:22:57
     */
    AccountPo select(Long id);

    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:22:57
     */
    int insert(AccountPo po);

    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:22:57
     */
    int update(AccountPo po);

    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:22:57
     */
    int delete(AccountPo po);
}