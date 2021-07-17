package com.runcoding.learn.integer;

public  class TestIntegerCache {

    public static void main(String[] args){

        Integer i1 = Integer.valueOf(100);//Integer i3 = 100;
        Integer i2 = Integer.valueOf(100);//Integer i4 = 100;
        System.out.println(i1 == i2); //true
        Integer i3 = Integer.valueOf(1000);//Integer i5 = 1000;
        Integer i4 = Integer.valueOf(1000);//Integer i6 = 1000;
        System.out.println(i3 == i4); //false



        /**
         Integer 在valueOf时缓存了[-128~127]
         public static Integer valueOf(int i) {
            if (i >= IntegerCache.low && i <= IntegerCache.high)
                return IntegerCache.cache[i + (-IntegerCache.low)];
            return new Integer(i);
         }
         * */
    }

}