package com.product.adapter.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.product.adapter.entities.Products;
import com.product.adapter.repository.ProductsRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@RunWith(SpringRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ProductRepositoryTest {

	private Logger log = LoggerFactory.getLogger(ProductRepositoryTest.class);
	
	@Autowired
	private ProductsRepository prdRepo;

	private List<Products> products = Arrays.asList(new Products("Apple 5", "Apple 5", 50000.0),
			new Products("Apple 6", "Apple 6", 60000.0), 
			new Products("Apple 7", "Apple 7", 70000.0),
			new Products("Apple 8", "Apple 8", 80000.0),
			new Products("Apple 9", "Apple 9", 90000.0),
			new Products("Apple 10", "Apple 10", 100000.0), 
			new Products("Apple 11", "Apple 11", 110000.0),
			new Products("Apple 12", "Apple 12", 120000.0));

	@BeforeAll
	@DisplayName("Setup Products")
	public  void initializeProduct() {
		log.info("Setting up product collection with default data for testing.");
		prdRepo.deleteAll()
				.thenMany(Flux.fromIterable(products))
				.flatMap(prd -> prdRepo.save(prd))
				.doOnNext((prd->{
					log.info("Product="+prd);
				}))
				.blockLast();
				
	}

	@Test
	@DisplayName("Find All Productcs")
	public void allProducts() {
		log.info("Find all products");
		StepVerifier.create(prdRepo.findAll())
				.expectSubscription()
				.expectNextCount(8)
				.verifyComplete();
	}

	@Test
	@DisplayName("Find Product By Name")
	public void findByName() {
		log.info("Find product by name");
		StepVerifier.create(prdRepo.findByName("Apple 5"))
			.expectSubscription()
			.expectNextCount(1)
			.verifyComplete();
	}
	
	@Test
	@DisplayName("Find Product By Active Status")
	public void findByActive() {
		log.info("Find Product By Active Status");
		StepVerifier.create(prdRepo.findByActive(false))
			.expectSubscription()
			.expectNextCount(0)
			.verifyComplete();
	}
	
	@Test
	@DisplayName("Save New Product")
	public void saveProduct() {
		Products _product = new Products("Apple 13", "Apple 13", 130000.0);
		log.info("Save New Product "+_product);
		Mono<Products> product = prdRepo.save(_product);
		
		StepVerifier.create(product)
					.expectSubscription()
					.expectNextMatches((prd) -> (prd!=null && prd.getId()!=null && prd.getName().equalsIgnoreCase("Apple 13")))
					.verifyComplete();
	}
	
	@Test
	@DisplayName("Update Product")
	public void updateProduct() {
		double newPrice = 48000;
		log.info("Update Product");
		Mono<Products> updatedProduct = prdRepo.findByName("Apple 5")
				.map((prd) ->{
					prd.setAmount(newPrice);
					return prd;
				})
				.flatMap(prd ->{
					return prdRepo.save(prd);
				});
		
		StepVerifier.create(updatedProduct)
					.expectSubscription()
					.expectNextMatches((prd) -> (prd!=null && prd.getId()!=null && prd.getAmount().equals(new Double(newPrice))))
					.verifyComplete();
	}
	
	
	
	
}
