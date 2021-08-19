package com.order.domainlayer.service;

import java.util.List;

import com.order.dto.ProductDTO;

import reactor.core.publisher.Mono;

public interface ProductIntegrationService {

	public Mono<List<ProductDTO>> getProductDetails(List<String> productId);

}
