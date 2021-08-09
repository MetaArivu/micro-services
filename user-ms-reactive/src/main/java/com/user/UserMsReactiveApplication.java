package com.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "APIs", version = "1.0", description = "User Service Documentation APIs v1.0"))
public class UserMsReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMsReactiveApplication.class, args);
	}

}
