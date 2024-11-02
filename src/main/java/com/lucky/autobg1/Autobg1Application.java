package com.lucky.autobg1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.lucky.autobg1")
public class Autobg1Application {

	public static void main(String[] args) {
		SpringApplication.run(Autobg1Application.class, args);
	}

}
