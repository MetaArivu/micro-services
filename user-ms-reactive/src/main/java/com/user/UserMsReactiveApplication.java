package com.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class UserMsReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMsReactiveApplication.class, args);
	}

}
