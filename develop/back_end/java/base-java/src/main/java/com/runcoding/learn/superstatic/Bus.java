package com.runcoding.learn.superstatic;

public class Bus extends  Car {

    static {
        System.out.println(" Bus static");
    }

    private static  String  carColor = getCarColor();

    static String getCarColor(){
        System.out.println(" Bus getCarColor()");
        return "red";
    }

    public Bus() {
        System.out.println(" Bus()");
    }

    public static void MyStatic(){
        System.out.println(" MyStatic()");
    }

    public static void main(String[] args) {
        Bus.MyStatic();
        new Bus();
        System.out.println("----------");
        System.out.println(" My carColor = "+carColor);
        carColor = "purple";
        Bus bus = new Bus();
        System.out.println(" My carColor = "+bus.carColor);
    }
}
