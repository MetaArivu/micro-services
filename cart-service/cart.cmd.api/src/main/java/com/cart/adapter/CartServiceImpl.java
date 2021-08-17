package com.cart.adapter;

import java.util.List;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.aggregate.CartItemRepo;
import com.cart.command.AddItemCommand;
import com.cart.command.CreateCartCommand;
import com.cart.core.exception.ServiceException;
import com.cart.core.model.CartItem;
import com.cart.domainlayer.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	private Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	private CommandGateway commandGateway;

	@Autowired
	private CartItemRepo cartRepo;
	
	@Override
	public void handle(AddItemCommand addItemCommand) throws ServiceException {

		
		
		AddItemCommand cmd = addItemCommand.cloneWithDefault();

		final String userid = cmd.getCartItem().getUserId();
		
		List<CartItem>  cartItems = cartRepo.findByUserIdAndProcessedAndActive(userid, false, true);

		// This event should bee raised only when all cart item are processed..
		if( cartItems.size() == 0 ) {
			this.createCardCommand(cmd);
			cmd.getCartItem().setId(cmd.getId());
		}else {
			for (CartItem cartItem : cartItems) {
				cmd.setId(cartItem.getAggrId());
			}
		}
		
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
