package com.runcoding.learn.pecs;

/**
 * @Author: runcoding
 * @Email: runcoding@163.com
 * @Created Time: 2017/9/5 17:19
 * @Description 通配符
 **/
public class Plate<T> {

    private T item;

    public Plate(T t){
        item=t;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
