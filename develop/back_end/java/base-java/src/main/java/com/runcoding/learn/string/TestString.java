package com.runcoding.learn.string;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * @Author: runcoding
 * @Email: runcoding@163.com
 * @Created Time: 2017/9/4 21:56
 * @Description
 **/
public class TestString {


    public static void main(String[] args) {

        List<Integer> list  = new ArrayList<>();
        list.add(3);
        list.add(1);

        Collections.sort(list);
        System.out.println(JSON.toJSONString(list));
        //Comparator<? super Integer> comparator = Comparator.comparingInt(o -> o);
        //list.sort(comparator);
       // System.out.println(JSON.toJSONString(list));

        String s3 = "s";
        String s4 = "s";
        System.out.println(s3==s4); //true

        String s5 = "RunningCoding";
        String s6 = "Running"+"Coding";
        System.out.println(s5==s6); //true

        String s1 = new String("s");
        String s2 = new String("s");
        System.out.println(s1==s2); //false
        System.out.println(s1.intern()==s2.intern()); //true

        /***
         * String s = “s” 是常量池中创建一个对象”s”，所以是true。
         * 而String s = new String（”s”）在堆上面分配内存创建一个String对象，栈放了对象引用。
         */

        String s11 = new StringBuilder("go").append("od").toString();
        System.out.println(s11.intern() == s11);
        String s22 = new StringBuilder("ja").append("va").toString();
        System.out.println(s22.intern() == s22);
    }
}
