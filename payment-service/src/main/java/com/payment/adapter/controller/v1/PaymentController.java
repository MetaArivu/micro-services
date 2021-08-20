package com.payment.adapter.controller.v1;

import static com.payment.constants.APIConstant.V1;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.adapter.entities.Payment;
import com.payment.domainlayer.service.PaymentService;
import com.payment.dto.Response;
import com.payment.exceptions.InvalidInputException;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(V1)
public class PaymentController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	private PaymentService paymentSvc;

	@PostMapping(value = "/")
	public Mono<ResponseEntity<Response<Payment>>> paymeent(@RequestBody Payment payment) {

		return paymentSvc.completePayment(payment)
				.map(paymentDetails -> {
						return new ResponseEntity<Response<Payment>>(new Response<Payment>(true, "Payment Completed Successfully", paymentDetails),
						HttpStatus.OK);
				})
				.defaultIfEmpty(new ResponseEntity<Response<Payment>>(new Response<Payment>(false, "Payment Cannot Be Processed"), HttpStatus.INTERNAL_SERVER_ERROR));

	}

	
	

}
