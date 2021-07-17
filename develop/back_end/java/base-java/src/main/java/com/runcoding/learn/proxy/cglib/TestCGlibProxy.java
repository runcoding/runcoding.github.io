package com.runcoding.learn.proxy.cglib;

public class TestCGlibProxy {
    public static void main(String[] args) {

		StudentCglib stu1 = (StudentCglib)(new CGlibProxyFactory().createProxy(new StudentCglib()));

		StudentCglib stu2 = (StudentCglib)(new CGlibProxyFactory().createProxy(new StudentCglib("Running Coding")));
		stu1.print();    
		stu2.print();    
	}    
}   