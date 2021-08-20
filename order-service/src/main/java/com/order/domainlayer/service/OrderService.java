package com.order.domainlayer.service;

import com.order.adapter.entities.Order;
import com.order.exceptions.InvalidInputException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {

	public Mono<Order> createOrder(Order _order);
	
	public Flux<Order> userOrderDetails();

}
