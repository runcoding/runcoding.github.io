package com.runcoding.learn.jvm;

class OOM {

    static final int SIZE=2*1024*1024;

    public static void main(String[] a) {
        int[] i = new int[SIZE];
    }

}