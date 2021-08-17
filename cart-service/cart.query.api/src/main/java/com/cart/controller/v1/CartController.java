package com.cart.controller.v1;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cart.core.exception.ServiceException;
import com.cart.core.model.CartItem;
import com.cart.domainlayer.service.CartQueryService;

@RestController
@RequestMapping("/api/v1")
public class CartController {

	private static final Logger log = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private CartQueryService cartQuerySvc;

	@GetMapping("/")
	public ResponseEntity<List<CartItem>> userCart() {

		try {
			return new ResponseEntity<List<CartItem>>(cartQuerySvc.userCart(), HttpStatus.OK);
		} catch (ServiceException e) {
			log.warn("ServiceException={}", e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<List<CartItem>>(HttpStatus.NOT_FOUND);
		}

	}
}
