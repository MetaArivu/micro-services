package com.cart.aggregate;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.command.AddItemCommand;
import com.cart.command.CreateCartCommand;
import com.cart.core.exception.ServiceException;
import com.cart.domainlayer.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	private Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	private CommandGateway commandGateway;

	@Override
	public void handle(AddItemCommand addItemCommand) throws ServiceException {

		AddItemCommand cmd = addItemCommand.cloneWithDefault();

		// This event should bee raised only when all cart item are processed..
		this.createCardCommand(cmd);
		
		cmd.getCartItem().setId(cmd.getId());
		commandGateway.sendAndWait(cmd);
		log.info("Add Item command raised, id={}", cmd.getId());
		
	}
	
	
	private void createCardCommand(AddItemCommand cmd) {
		CreateCartCommand createCartCommand = CreateCartCommand
				.builder()
				.id(cmd.getId())
				.cartItem(cmd.getCartItem().init())
				.build();

		commandGateway.sendAndWait(createCartCommand);
		log.info("Create cart command raised, id={}", createCartCommand.getId());
	}

}
