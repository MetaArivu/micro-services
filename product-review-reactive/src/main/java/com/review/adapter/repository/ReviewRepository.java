package com.review.adapter.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.review.adapter.entities.ProductReview;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReviewRepository extends ReactiveMongoRepository<ProductReview, String> {

	public Flux<ProductReview> findByProductIdAndActive(String id, boolean active);
		

}
