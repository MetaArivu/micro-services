package com.payment.adapter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.payment.adapter.entities.Payment;
import com.payment.adapter.repository.PaymentRepository;
import com.payment.domainlayer.service.PaymentService;
import com.payment.event.PaymentEvent;
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

	@Autowired
	private KafkaTemplate<String, PaymentEvent> kafkaTemplate;
	
	@Override
	public Mono<Payment> completePayment(Payment payment) {
		payment.setSuccess(true);
		payment.setRemark("Payment Completed");
		return paymentRepo.save(payment)
				.flatMap(pay->{
					this.publishEvent(pay);
					return Mono.just(new Payment(pay.getId(), pay.getReferenceId(), pay.isSuccess(), pay.getRemark()));
				}).onErrorMap(e->{
					return  new PaymentException(e.getMessage());
				});
	}
	
	private void publishEvent(Payment payment) {
		log.info(null);
		PaymentEvent event = PaymentEvent.builder()
										.paymentId(payment.getId())
										.referenceId(payment.getReferenceId())
										.success(payment.isSuccess())
										.remarks(payment.getRemark())
										.date(payment.getDate())
										.build();
		
		log.info("Payment Details Event Published={}", event);
		kafkaTemplate.send("payment_details", event);
	}
}
