package com.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class ProductMsReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductMsReactiveApplication.class, args);
	}

}
