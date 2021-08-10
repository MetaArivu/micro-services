package com.review.domainlayer.service;

import com.review.adapter.entities.ProductReview;
import com.review.exceptions.InvalidInputException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReviewService {

	public Flux<ProductReview> reviewsByProduc(String productId);
	
 	public Mono<ProductReview> save(ProductReview _product);
		
	public Mono<ProductReview> delete(String _id);
}
