package com.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "APIs", version = "1.0", description = "Products Service Documentation APIs v1.0"))
public class ProductMsReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductMsReactiveApplication.class, args);
	}

}
