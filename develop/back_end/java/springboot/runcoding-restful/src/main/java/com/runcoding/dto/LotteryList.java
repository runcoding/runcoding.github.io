package com.runcoding.dto;

import java.util.List;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018-12-27 21:16
 * @description Copyright (C), 2017-2018,
 **/
@lombok.NoArgsConstructor
@lombok.Data
public class LotteryList {


    /**
     * code : 1
     * msg : 数据返回成功
     * data : {"page":199999,"totalCount":918,"totalPage":92,"limit":10,"list":[{"openCode":"03,08,17,21,25,32+15","code":"ssq","expect":"2013008","name":"双色球","time":"2013-01-17 21:18:20"},{"openCode":"02,09,15,22,26,32+01","code":"ssq","expect":"2013007","name":"双色球","time":"2013-01-15 21:18:20"},{"openCode":"09,10,13,17,22,30+13","code":"ssq","expect":"2013006","name":"双色球","time":"2013-01-13 21:18:20"},{"openCode":"01,13,14,25,31,32+12","code":"ssq","expect":"2013005","name":"双色球","time":"2013-01-10 21:18:20"},{"openCode":"06,10,16,20,27,32+08","code":"ssq","expect":"2013004","name":"双色球","time":"2013-01-08 21:18:20"},{"openCode":"22,23,26,27,28,33+09","code":"ssq","expect":"2013003","name":"双色球","time":"2013-01-06 21:18:20"},{"openCode":"01,16,18,22,28,30+12","code":"ssq","expect":"2013002","name":"双色球","time":"2013-01-03 21:18:20"},{"openCode":"06,08,14,15,24,25+06","code":"ssq","expect":"2013001","name":"双色球","time":"2013-01-01 21:18:20"}]}
     */

    private int code;
    private String msg;
    private DataDto data;

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class DataDto {
        /**
         * page : 199999
         * totalCount : 918
         * totalPage : 92
         * limit : 10
         * list : [{"openCode":"03,08,17,21,25,32+15","code":"ssq","expect":"2013008","name":"双色球","time":"2013-01-17 21:18:20"},{"openCode":"02,09,15,22,26,32+01","code":"ssq","expect":"2013007","name":"双色球","time":"2013-01-15 21:18:20"},{"openCode":"09,10,13,17,22,30+13","code":"ssq","expect":"2013006","name":"双色球","time":"2013-01-13 21:18:20"},{"openCode":"01,13,14,25,31,32+12","code":"ssq","expect":"2013005","name":"双色球","time":"2013-01-10 21:18:20"},{"openCode":"06,10,16,20,27,32+08","code":"ssq","expect":"2013004","name":"双色球","time":"2013-01-08 21:18:20"},{"openCode":"22,23,26,27,28,33+09","code":"ssq","expect":"2013003","name":"双色球","time":"2013-01-06 21:18:20"},{"openCode":"01,16,18,22,28,30+12","code":"ssq","expect":"2013002","name":"双色球","time":"2013-01-03 21:18:20"},{"openCode":"06,08,14,15,24,25+06","code":"ssq","expect":"2013001","name":"双色球","time":"2013-01-01 21:18:20"}]
         */

        private int page;
        private int totalCount;
        private int totalPage;
        private int limit;
        private List<LotteryDto> list;


    }
}
