package com.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.cart.core.config.DBConfiguration;

@SpringBootApplication
@Import({ DBConfiguration.class })
public class CartQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartQueryApplication.class, args);
	}

}
