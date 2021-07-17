package com.runcoding.learn.superstatic;

public class Car {

    static {
        System.out.println(" Car static");
    }

    public Car() {
        System.out.println(" Car()");
    }
}
