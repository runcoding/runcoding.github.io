package com.runcoding.learn.proxy;

public class TestProxy {

    public static void main(String args[]) {
        ProxyFactory proxyFactory = new ProxyFactory();
        Student student = new Student("runcoding");
        StudentInterface studentBean = (StudentInterface) proxyFactory.createProxy(student);
		studentBean.username();
    }

} 