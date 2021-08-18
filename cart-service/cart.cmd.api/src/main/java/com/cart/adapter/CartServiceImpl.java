package com.cart.adapter;

import java.util.List;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import com.cart.aggregate.CartItemRepo;
import com.cart.command.AddItemCommand;
import com.cart.command.CreateCartCommand;
import com.cart.command.RemoveItemCommand;
import com.cart.core.exception.ServiceException;
import com.cart.core.model.CartItem;
import com.cart.core.model.RequestHelper;
import com.cart.domainlayer.service.CartService;
import com.cart.dto.ProductDTO;
import com.netflix.discovery.converters.Auto;

@Service
@RequestScope
public class CartServiceImpl implements CartService {

	private Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	private CommandGateway commandGateway;

	@Autowired
	private CartItemRepo cartRepo;
	
	@Autowired
	private ProductIntegrationServiceImpl prdIntSvc;
	
	@Autowired
	private RequestHelper helper;
	
	
	@Override
	public void handle(AddItemCommand addItemCommand) throws ServiceException {
		
		ProductDTO prd = prdIntSvc.getProductDetails(addItemCommand.getCartItem().getPrdId());
		
		addItemCommand.getCartItem().setProductName(prd.getName());
		addItemCommand.getCartItem().setPricePerUnit(prd.getAmount());
		System.out.println("PRD="+prd);
		AddItemCommand cmd = addItemCommand.cloneWithDefault(helper.getUserId());

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
				.cartItem(cmd.getCartItem().init(helper.getUserId()))
				.build();

		commandGateway.sendAndWait(createCartCommand);
		log.info("Create cart command raised, id={}", createCartCommand.getId());
	}

	@Override
	public void handle(RemoveItemCommand removeItemCommand) throws ServiceException {
		
		RemoveItemCommand cmd = removeItemCommand.cloneWithDefault(helper.getUserId());

		final String userid = cmd.getCartItem().getUserId();
		
		List<CartItem>  cartItems = cartRepo.findByUserIdAndProcessedAndActive(userid, false, true);

		// This event should bee raised only when all cart item are processed..
		if( cartItems.size() >0 )  {
			for (CartItem cartItem : cartItems) {
				cmd.setId(cartItem.getAggrId());
			}
			commandGateway.sendAndWait(cmd);
			log.info("Remove Item command raised, id={}", cmd.getId());
		} else {
			throw new ServiceException("User cart not created");
		}
		
		
		
	}
}
