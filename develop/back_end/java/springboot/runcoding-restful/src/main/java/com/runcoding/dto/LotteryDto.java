package com.runcoding.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018-12-27 21:34
 * @description Copyright (C), 2017-2018,
 **/
@NoArgsConstructor
@Data
public class LotteryDto {

    /**
     * openCode : 03,08,17,21,25,32+15
     * code : ssq
     * expect : 2013008
     * name : 双色球
     * time : 2013-01-17 21:18:20
     */

    private String openCode;
    private String code;
    private String expect;
    private String name;
    private String time;
}
