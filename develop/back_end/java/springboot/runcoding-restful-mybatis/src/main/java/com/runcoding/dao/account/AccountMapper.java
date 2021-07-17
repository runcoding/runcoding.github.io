package com.runcoding.dao.account;

import com.runcoding.model.po.account.AccountPo;
import org.apache.ibatis.annotations.Param;


/**
 * @author runcoding
 * @Date 2018-01-02 17:22:57
 */
public interface AccountMapper {
    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:22:57
     */
    AccountPo select(@Param("id") Long id);

    
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