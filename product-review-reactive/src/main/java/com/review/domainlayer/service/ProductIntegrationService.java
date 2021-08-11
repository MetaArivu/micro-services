package com.review.domainlayer.service;

import com.review.dto.ProductDTO;

import reactor.core.publisher.Mono;

public interface ProductIntegrationService {

	public Mono<ProductDTO> getProductDetails(String productId);

}
