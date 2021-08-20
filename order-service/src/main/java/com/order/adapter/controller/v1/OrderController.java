package com.order.adapter.controller.v1;

import static com.order.constants.APIConstant.V1;

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

import com.order.adapter.entities.Order;
import com.order.domainlayer.service.OrderService;
import com.order.dto.Response;
import com.order.exceptions.InvalidInputException;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(V1)
public class OrderController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderSvc;

	@PostMapping(value = "/")
	public Mono<ResponseEntity<Response<Order>>> save(@RequestBody Order order) {

		return orderSvc.createOrder(order)
				.map(prd -> {
						return new ResponseEntity<Response<Order>>(new Response<Order>(true, "Order Created Successully", new  Order(prd.getId(), prd.getOrderNo())),
						HttpStatus.OK);
				})
				.defaultIfEmpty(new ResponseEntity<Response<Order>>(new Response<Order>(false, "Cannot Place Order"), HttpStatus.NOT_FOUND));

	}
	
	
	@GetMapping(value = "/")
	public Mono<ResponseEntity<Response<List<Order>>>> userOrderDetails() {

		return orderSvc.userOrderDetails()
				.collectList()
				.map(orders -> {
					return new ResponseEntity<Response<List<Order>>>(new Response<List<Order>>(true, "User Order Details", orders),
					HttpStatus.OK);
				})
				.defaultIfEmpty(new ResponseEntity<Response<List<Order>>>(new Response<List<Order>>(false, "User has not placed any order"), HttpStatus.NOT_FOUND));

	}
	
	

}
