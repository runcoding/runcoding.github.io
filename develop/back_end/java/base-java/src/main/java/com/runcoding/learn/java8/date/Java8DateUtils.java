package com.runcoding.learn.java8.date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Java8DateUtils {

    private static Logger  logger = LoggerFactory.getLogger(Java8DateUtils.class);

    public static void main(String[] args) {
        /**显示日期*/
        Java8DateUtils.localDate();
        /**显示时间*/
        Java8DateUtils.localDateTime();
        /**某一个时间点的时间戳*/
        Java8DateUtils.instant();
    }

    /**显示日期*/
    public static void localDate(){
        logger.info("-----start localDate() ----");
        LocalDate date = LocalDate.now();
        logger.info("默认格式日期：{}",date);//2017-09-04
        logger.info("BASIC_ISO_DATE格式日期：{}",date.format(DateTimeFormatter.BASIC_ISO_DATE));//20170904
        logger.info("ISO_LOCAL_DATE格式日期：{}",date.format(DateTimeFormatter.ISO_LOCAL_DATE));//2017-09-04

        logger.info("今天的开始日期：{}",date.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));//2017-09-04 00:00:00
        logger.info("-----end localDate() ----\n");
    }

    /**显示时间*/
    public static  void localDateTime(){
        logger.info("-----start localDateTime() ----");
        LocalDateTime date = LocalDateTime.now();
        logger.info("默认格式时间：{}",date);//2017-09-04T14:09:29.288
        logger.info("BASIC_ISO_DATE格式日期：{}",date.format(DateTimeFormatter.BASIC_ISO_DATE));//20170904
        logger.info("ISO_LOCAL_DATE格式日期：{}",date.format(DateTimeFormatter.ISO_LOCAL_DATE));//2017-09-04
        logger.info("yyyy-MM-dd HH:mm:ss格式日期：{}",date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));//2017-09-04 14:09:29
        logger.info("-----end localDateTime() ----\n");
    }

    /**某一个时间点的时间戳*/
    public static void  instant(){
        logger.info("-----start instant() ----");
        Instant date = Instant.now();
        logger.info("默认格式时间：{}",date.toString());//2017-09-04T06:09:29.289Z
        logger.info("当前时间加5s：{}",date.plus(5, ChronoUnit.SECONDS));//2017-09-04T06:09:34.289Z
        logger.info("减去1000毫秒数：{}",date.minusMillis(1000));//2017-09-04T06:09:28.289Z
        logger.info("是否在当前时间之前：{}",date.isBefore(Instant.now()));//true(可以会相同)
        logger.info("是否在当前时间之后：{}",date.isAfter(Instant.now())); //false
        logger.info("compareTo比较大小（当前Instant大，返回正数；相等，返回0；否则，返回负数）：{}",Instant.now().compareTo(date));//3000000
        logger.info("从1970-01-01 00:00:00开始的秒数：{}",date.getEpochSecond());//1504505369
        logger.info("从1970-01-01 00:00:00开始的毫秒数：{}",date.toEpochMilli());//1504505369289
        logger.info("-----end instant() ----\n");
    }



}
