package com.spartajet.shardingboot.strategy;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.MultipleKeysTableShardingAlgorithm;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

/**
 * @description
 * @create 2017-02-07 下午9:23
 * @email gxz04220427@163.com
 */
@Service
public class TableShardingAlgorithm implements MultipleKeysTableShardingAlgorithm {
    /**
     * 根据分片值计算分片结果名称集合.
     *
     * @param availableTargetNames 所有的可用目标名称集合, 一般是数据源或表名称
     * @param shardingValues       分片值集合
     *
     * @return 分片后指向的目标名称集合, 一般是数据源或表名称
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, Collection<ShardingValue<?>> shardingValues) {
        String name = null;
        Date time = null;
        for (ShardingValue<?> shardingValue : shardingValues) {
            if (shardingValue.getColumnName().equals("name")) {
                name = ((ShardingValue<String>) shardingValue).getValue();
            }
            if (shardingValue.getColumnName().equals("time")) {
                time = ((ShardingValue<Date>) shardingValue).getValue();
            }
            if (name != null && time != null) {
                break;
            }
        }
        String timeString = new SimpleDateFormat("yyyy_MM").format(time);
        String suffix = name + "_" + timeString;
        Collection<String> result = new LinkedHashSet<>();
        for (String targetName : availableTargetNames) {
            if (targetName.endsWith(suffix)) {
                result.add(targetName);
            }
        }
        return result;
    }
}
