package com.product.adapter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.adapter.entities.Products;
import com.product.adapter.repository.ProductsRepository;
import com.product.domainlayer.service.ProductService;
import com.product.exceptions.DuplicateRecordException;
import com.product.exceptions.InvalidInputException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductsRepository prdRepo;
	
	@Override
	public Flux<Products> allProducts() {
		return prdRepo.findByActive(true);
	}

	@Override
	public Mono<Products> findById(String _id){
		
		if(_id == null) {
			Mono<Products> fallback = Mono.error( new InvalidInputException("Invalid Id="+_id));
			return fallback;
		}
		
		return prdRepo.findByIdAndActive(_id, true);
	}

	@Override
	public Mono<Products> save(Products _product) {
		
		if(_product==null || !_product.isValid()) {
			Mono<Products> fallback = Mono.error( new InvalidInputException(Products.invalidMsg()));
			return fallback;
		}
		
		return prdRepo.findByName(_product.getName())
				.flatMap(prd ->{
				    Mono<Products> fallback = Mono.error(new DuplicateRecordException("Duplicate Product Name " + _product.getName()));
					return fallback;
				})
				.switchIfEmpty(prdRepo.save(_product));
	}

	@Override
	public Mono<Products> update(String id, Products _product) {
		
		return prdRepo.findById(id)
				.map((prd) ->{
					prd.setAmount(_product.getAmount());
					prd.setName(_product.getName());
					prd.setDesc(_product.getDesc());
					return prd;
				})
				.flatMap(prd ->{
					return prdRepo.save(prd);
				});
	}

	@Override
	public Mono<Products> delete(String _id) {
		
		return prdRepo.findById(_id)
				.map((prd) ->{
					prd.setActive(false);
					return prd;
				})
				.flatMap(prd ->{
					return prdRepo.save(prd);
				});
	}
	
	

}
