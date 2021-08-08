package com.product.adapter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.product.domainlayer.service.ProductService;

@ExtendWith(SpringExtension.class)
@WebFluxTest
public class ProductControllerTest {

	@Autowired
	WebTestClient webTestClient;

	@MockBean
	ProductService prdSvc;
	
	/*
	@Test
	public void get_all_products() {

		List<Integer> productIds = Arrays.asList(1,2,3);
		 
		EntityExchangeResult<List<Integer>> entityExchangeResult = webTestClient
						  .get().uri("/api/v1/")
						  .exchange()
						  .expectStatus()
						  .isOk()
						  .expectBodyList(Integer.class)
			              .returnResult();

		 assertEquals(productIds,entityExchangeResult.getResponseBody());
	}

	
	@Test
	public void get_product() {

		String productId = "1";
		 
		EntityExchangeResult<String> entityExchangeResult = webTestClient.get().uri("/api/v1/"+productId)
						  .exchange()
						  .expectStatus()
						  .isOk()
						  .expectBody(String.class)
			              .returnResult();

		  assertEquals(productId,entityExchangeResult.getResponseBody());
	}
	*/

}
