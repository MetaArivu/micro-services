package com.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "APIs", version = "1.0", description = "Review Service Documentation APIs v1.0"))
public class ReviewMsReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewMsReactiveApplication.class, args);
	}

}
