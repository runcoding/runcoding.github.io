package com.runcoding.learn.proxy;

public class Student implements StudentInterface{

    private String name;

	public Student(){}

	public Student(String name){
		this.name = name;
	}

	public void username(){
		System.out.println("学生的名称："+name);
	}

	public String getName() {    
		return name;    
	}

	public void setName(String name) {    
		this.name = name;    
	}    
}    