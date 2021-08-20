package com.payment.domainlayer.service;

import com.payment.adapter.entities.Payment;
import com.payment.exceptions.InvalidInputException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentService {

	public Mono<Payment> completePayment(Payment _order);
	

}
