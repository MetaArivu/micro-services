package com.payment.adapter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.adapter.entities.Payment;
import com.payment.adapter.repository.PaymentRepository;
import com.payment.domainlayer.service.PaymentService;
import com.payment.exceptions.PaymentException;
import com.payment.server.secutiry.JWTUtil;

import reactor.core.publisher.Mono;

@Service
public class PaymentServiceImpl implements PaymentService {

	private static final Logger log = (Logger) LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Autowired
	private PaymentRepository paymentRepo;

	@Autowired
	private JWTUtil jwtUtil;

	@Override
	public Mono<Payment> completePayment(Payment payment) {
		return paymentRepo.save(payment)
				.flatMap(pay->{
					return Mono.just(new Payment(pay.getId(), pay.getReferenceId(), true, "Payment Completed"));
				}).onErrorMap(e->{
					return  new PaymentException(e.getMessage());
				});
	}
}
