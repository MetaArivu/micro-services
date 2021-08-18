package com.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import com.cart.core.config.DBConfiguration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "APIs", version = "1.0", description = "Cart Query Service Documentation APIs v1.0"))
@Import({ DBConfiguration.class })
public class CartQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartQueryApplication.class, args);
	}

}
