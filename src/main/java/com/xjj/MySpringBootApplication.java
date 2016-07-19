package com.xjj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MySpringBootApplication {
	private static Logger logger = LoggerFactory.getLogger(MySpringBootApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(MySpringBootApplication.class, args);
		logger.info("My Spring Boot Application Started");
	}
}
