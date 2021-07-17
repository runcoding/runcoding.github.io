package com.runcoding.learn.map;

import java.util.LinkedHashMap;

/**
 * @Author: runcoding
 * @Email: runcoding@163.com
 * @Created Time: 2017/9/6 09:12
 * @Description
 **/
public class TestLinkedHashMap {


    public static void main(String[] args) {
        LinkedHashMap<String , String> hashMap = new LinkedHashMap();
        hashMap.put("username1","runcoding");
        String username =  hashMap.get("username");

    }
}
