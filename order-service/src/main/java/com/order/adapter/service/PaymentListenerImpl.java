package com.order.adapter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.domainlayer.service.OrderService;
import com.order.domainlayer.service.PaymentListener;
import com.order.event.PaymentEvent;
import com.order.exceptions.PaymentException;

@Service
public class PaymentListenerImpl implements PaymentListener {

	private Logger log = LoggerFactory.getLogger(PaymentListenerImpl.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private OrderService ordSvc;

	@Override
	@KafkaListener(topics = "payment_details")
	public void onPaymentEvent(String paymentEvent) {
		log.info("Payment Event Received, {}", paymentEvent);

		PaymentEvent event = PaymentEvent.getInstance(paymentEvent, objectMapper);
		try {
			ordSvc.updatePaymentDetails(event)
				.subscribe((ord)->{
					log.info("Payment details updated for orderid={} with paymentid={}", ord.getId(), ord.getPaymentId());
				});
		} catch (PaymentException e) {
			log.error("PaymentException={}", e.getMessage());
			e.printStackTrace();
		}

	}

}
