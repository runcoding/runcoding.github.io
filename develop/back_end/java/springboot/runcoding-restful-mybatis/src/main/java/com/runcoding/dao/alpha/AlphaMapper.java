package com.runcoding.dao.alpha;

import org.apache.ibatis.annotations.Param;


/**
 * @author runcoding
 * @Date 2018-01-02 17:22:57
 */
public interface AlphaMapper {


    void insert(@Param("word") String word,@Param("translation") String translation);


}