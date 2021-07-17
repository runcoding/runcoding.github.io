package com.runcoding.binlog;

import com.alibaba.fastjson.JSON;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2019-02-23 21:49
 * @description Copyright (C), 2017-2019,
 **/
//@Component
public class MysqlBinlogConnector implements CommandLineRunner {
    @Override
    public void run(String... strings) throws Exception {
        BinaryLogClient client = new BinaryLogClient("localhost", 3306, "root", "mysql");
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        client.setEventDeserializer(eventDeserializer);
        client.registerEventListener(new BinaryLogClient.EventListener() {

            @Override
            public void onEvent(Event event) {
                EventData data = event.getData();
                if(data instanceof QueryEventData){
                    String sql = ((QueryEventData) data).getSql();
                    System.out.println("sql="+sql);
                }else if(data instanceof TableMapEventData){
                    String table = ((TableMapEventData) data).getTable();
                    System.out.println("table="+table);
                }else if(data instanceof UpdateRowsEventData){
                    System.out.println("Update="+data.toString());
                }else if(data instanceof WriteRowsEventData){
                    System.out.println("Write="+data.toString());
                }else if(data instanceof DeleteRowsEventData){
                    System.out.println("Delete="+data.toString());
                }else {
                   System.out.println("event="+ JSON.toJSONString(event));
                }
            }
        });
        client.connect();
    }
}
