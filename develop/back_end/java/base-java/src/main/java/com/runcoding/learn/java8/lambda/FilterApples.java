package com.runcoding.learn.java8.lambda;

import java.util.Arrays;
import java.util.List;

public class FilterApples {

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList( new Apple(50, "green"),new Apple(100, "red"));
        inventory.forEach(FilterApples::getApple);
        /**
         apple = [green]
         apple = [red]
         * */
    }

    public static void getApple(Apple apple) {
        System.out.println("apple = [" + apple.getColor() + "]");
    }



}

 class Apple {

    private int weight = 0;

    private String color = "";

    public Apple(int weight, String color) {
        this.weight = weight;
        this.color = color;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


}

