package com.product.domainlayer.service;

import com.product.adapter.entities.Products;
import com.product.exceptions.InvalidInputException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

	public Flux<Products> allProducts();
	
	public Mono<Products> findById(String _id);
	
	public Mono<Products> save(Products _product);
	
	public Mono<Products> update(String id, Products _product);
	
	public Mono<Products> delete(String _id);
}
