package com.runcoding.learn.initclass;

public  class InitClass{

    static {
        System.out.println("初始化InitClass");  
    }

    public static String runCoding = "RunningCoding";

    public static void methodA(){
        System.out.println("Running InitClass.method()");
    }

    public static void methodB(Integer i){
        System.out.println("Running InitClass.method() i ="+i);
    }


}
