package com.runcoding.learn.proxy.cglib;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGlibProxyFactory implements MethodInterceptor {

    private Object object;

	public Object createProxy(Object object){
		this.object = object;    
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(object.getClass());    
		enhancer.setCallback(this);    
		return enhancer.create();    
	}

	@Override    
	public Object intercept(Object proxy, Method method, Object[] args,
							MethodProxy methodProxy) throws Throwable {
	    if(object instanceof  StudentCglib){
            System.out.println("方法已经被拦截...");
        }
        return methodProxy.invoke(object, args);
	}    
}   