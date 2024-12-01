package com.lucky.autobg1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.lucky.autobg1")
@EnableScheduling
@ComponentScan(basePackages = "com.lucky.autobg1")
public class Autobg1Application extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(Autobg1Application.class, args);
	}

}
