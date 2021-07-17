package com.runcoding.learn.pecs;

import com.runcoding.learn.pecs.fruit.Fruit;
import com.runcoding.learn.pecs.fruit.apple.Apple;

/**
 * @Author: runcoding
 * @Email: runcoding@163.com
 * @Created Time: 2017/9/5 17:22
 * @Description
 **/
public class TestPecs {

    public static void main(String[] args) {
        /**1. 上界<? extends T>不能往里存，只能往外取*/
        Plate<? extends Fruit> p = new Plate<Apple>(new Apple());

        //不能存入任何元素
       // p.setItem(new Fruit());    //Error
       // p.setItem(new Apple());    //Error

        //读取出来的东西只能存放在Fruit或它的基类里。
        Object obj   = p.getItem(); //顶级父类(超类)
        Food  food   = p.getItem(); //父类
        Fruit fruit  = p.getItem(); //上界类
       // Apple apple  = p.getItem(); //本类Error


        /**2. 下界<? super T>不影响往里存，但往外取只能放在Object对象里*/
        Plate<? super Fruit> pSuper = new Plate<Fruit>(new Fruit());
        //存入元素正常
        pSuper.setItem(new Fruit());
        pSuper.setItem(new Apple());

        //读取出来的东西只能存放在Object类里。
       // Apple appleS  = pSuper.getItem();    //Error
       // Fruit fruitS  = pSuper.getItem();    //Error
        Object objS = p.getItem();
    }


}
