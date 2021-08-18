package com.cart.domainlayer.service;

import com.cart.dto.ProductDTO;

import reactor.core.publisher.Mono;

public interface ProductIntegrationService {

	public ProductDTO getProductDetails(String productId);

}
