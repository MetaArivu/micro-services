package com.order.domainlayer.service;

import com.order.adapter.entities.Order;
import com.order.event.PaymentEvent;
import com.order.exceptions.PaymentException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {

	public Mono<Order> createOrder(Order _order);
	
	public Flux<Order> userOrderDetails();
	
	public Mono<Order> updatePaymentDetails(PaymentEvent paymentEvent) throws PaymentException;

}
