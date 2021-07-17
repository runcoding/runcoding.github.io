package com.runcoding.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * @author runcoding
 */
@SpringBootConfiguration
@EnableAutoConfiguration
public class GatewaySampleApplication {



	public static void main(String[] args) {
		SpringApplication.run(GatewaySampleApplication.class, args);
	}



}