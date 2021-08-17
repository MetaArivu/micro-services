package com.cart.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cart.command.AddItemCommand;
import com.cart.command.RemoveItemCommand;
import com.cart.core.exception.ServiceException;
import com.cart.domainlayer.service.CartService;

@RestController
@RequestMapping("/api/v1/")
public class CartController {

	private Logger log = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private CartService cartSvc;

	@PostMapping("/add")
	public ResponseEntity<String> addItemEvent(@RequestBody AddItemCommand addItemCommand) {
		try {
			cartSvc.handle(addItemCommand);
			return new ResponseEntity<String>("Create card command", HttpStatus.OK);

		} catch (ServiceException e) {
			log.error("ServiceException={}", e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<String>("Create card command", HttpStatus.BAD_REQUEST);

		}
	}
	
	@PostMapping("/remove")
	public ResponseEntity<String> removeItemEvent(@RequestBody RemoveItemCommand removeItemCommand) {
		try {
			cartSvc.handle(removeItemCommand);
			return new ResponseEntity<String>("Create card command", HttpStatus.OK);

		} catch (ServiceException e) {
			log.error("ServiceException={}", e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<String>("Create card command", HttpStatus.BAD_REQUEST);

		}
	}
}
