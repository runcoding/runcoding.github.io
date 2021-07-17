package com.runcoding.learn.proxy.cglib;

public class StudentCglib {

    private String name;

	public StudentCglib(){

	}

	public StudentCglib(String name){
		this.name = name;
	}

	public void print(){
		System.out.println(name +" hello world!");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;    
	}    
} 