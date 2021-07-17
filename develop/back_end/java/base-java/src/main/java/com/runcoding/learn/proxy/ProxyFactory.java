package com.runcoding.learn.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory implements   InvocationHandler {

    private static Logger logger = LoggerFactory.getLogger(ProxyFactory.class);

    private Object object;

    public ProxyFactory() {
    }

    public Object createProxy(Object object){
		this.object = object;
		return Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(), this);
	}
	@Override    
	public Object invoke(Object proxy, Method method, Object[] args)
			            throws Throwable {
			object = method.invoke(object, args);
		return object;    
	}
}