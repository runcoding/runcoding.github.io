package com.runcoding.spring.boot.kafka.producer;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Date;
import java.util.UUID;


@Component
public class Producer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private static Gson gson = new GsonBuilder().create();

    //发送消息方法
    public void send() {
        Message message = new Message();
        message.setId("KFK_"+System.currentTimeMillis());
        message.setMsg(UUID.randomUUID().toString());
        message.setSendTime(new Date());
        ListenableFuture mykafka = kafkaTemplate.send("mykafka", gson.toJson(message));

        System.out.println(JSON.toJSONString(mykafka));

    }

}
