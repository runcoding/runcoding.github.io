package com.runcoding.test;


import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.repository.SchemaRepository;
import com.alibaba.druid.util.JdbcConstants;
import org.junit.jupiter.api.Test;

public class SQLUtilsTest {

    @Test
    public void createSql(){
        final String dbType = JdbcConstants.MYSQL;
        SchemaRepository repository = new SchemaRepository(dbType);

        String sql = "create table `table_name`(\n" +
                "id int,\n" +
                "name varchar(32),\n" +
                "primary key(id)\n" +
                ");";

        repository.console(sql);
        repository.console("ALTER TABLE `table_name` ADD COLUMN `type`  tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '优惠类型' AFTER `id`;");
        System.out.println(repository.console("desc table_name"));
        MySqlCreateTableStatement createTableStmt = (MySqlCreateTableStatement) repository.findTable("table_name").getStatement();
        String sqlString = SQLUtils.toSQLString(createTableStmt,dbType);
        System.out.println(sqlString);
    }

}
