package com.product.adapter.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.product.adapter.entities.Products;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductsRepository extends ReactiveMongoRepository<Products, String> {

	public Mono<Products> findByIdAndActive(String id, boolean active);
	
	public Mono<Products> findByName(String name);
	
	public Flux<Products> findByActive(boolean active);
	
	public Flux<Products> findByIdInAndActive(List<String> id, boolean active);
}
