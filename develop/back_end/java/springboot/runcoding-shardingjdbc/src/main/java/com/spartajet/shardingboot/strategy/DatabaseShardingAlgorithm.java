package com.spartajet.shardingboot.strategy;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @description
 * @create 2017-02-07 下午9:15
 * @email gxz04220427@163.com
 */
@Service
public class DatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<String> {
    /**
     * 根据分片值和SQL的=运算符计算分片结果名称集合.
     *
     * @param availableTargetNames 所有的可用目标名称集合, 一般是数据源或表名称
     * @param shardingValue        分片值
     *
     * @return 分片后指向的目标名称, 一般是数据源或表名称
     */
    @Override
    public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<String> shardingValue) {
        String databaseName = "";
        for (String targetName : availableTargetNames) {
            if (targetName.endsWith(shardingValue.getValue())) {
                databaseName = targetName;
                break;
            }
        }
        return databaseName;
    }

    /**
     * 根据分片值和SQL的IN运算符计算分片结果名称集合.
     *
     * @param availableTargetNames 所有的可用目标名称集合, 一般是数据源或表名称
     * @param shardingValue        分片值
     *
     * @return 分片后指向的目标名称集合, 一般是数据源或表名称
     */
    @Override
    public Collection<String> doInSharding(Collection<String> availableTargetNames, ShardingValue<String> shardingValue) {
        return null;
    }

    /**
     * 根据分片值和SQL的BETWEEN运算符计算分片结果名称集合.
     *
     * @param availableTargetNames 所有的可用目标名称集合, 一般是数据源或表名称
     * @param shardingValue        分片值
     *
     * @return 分片后指向的目标名称集合, 一般是数据源或表名称
     */
    @Override
    public Collection<String> doBetweenSharding(Collection<String> availableTargetNames, ShardingValue<String> shardingValue) {
        return null;
    }
}
