package com.spartajet.shardingboot.mapper;

import com.spartajet.shardingboot.bean.Tick;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description
 * @create 2017-02-07 下午9:58
 * @email gxz04220427@163.com
 */
@Mapper
public interface TickMapper {
    @Insert("insert into tick (id,name,exchange,ask,bid,time) values (#{id},#{name},#{exchange},#{ask},#{bid},#{time})")
    void insertTick(Tick tick);
}
