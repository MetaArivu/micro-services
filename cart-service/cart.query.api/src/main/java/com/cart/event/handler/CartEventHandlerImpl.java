package com.cart.event.handler;

import java.util.List;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.core.event.ItemAddedEvent;
import com.cart.core.event.RemoveItemEvent;
import com.cart.core.model.CartItem;
import com.cart.repo.CartItemsRepo;

@Service
@ProcessingGroup("cart-group")
public class CartEventHandlerImpl implements CartEventHandler {

	public static final Logger log = LoggerFactory.getLogger(CartEventHandler.class);

	@Autowired
	private CartItemsRepo cartRepo;

	@Override
	@EventHandler
	public void on(ItemAddedEvent itemAdded) {

		log.info("Item added eveent, {} ", itemAdded);
		CartItem cartItem = itemAdded.getCartItem();

		List<CartItem> cartItems = cartRepo.findByPrdIdAndUserIdAndProcessedAndActive(cartItem.getPrdId(), cartItem.getUserId(), false, true);

		for (CartItem cartItem2 : cartItems) {
			cartItem2.deActivee();
			cartRepo.save(cartItem2);
		}

		CartItem cartItem2 = cartItem.cloneAllWithDefault();
		cartItem2.setAggrId(itemAdded.getId());
		cartItem2 = cartRepo.save(cartItem2);

		log.info("Cart item added, id={}", cartItem2.getId());
	}

	@Override
	@EventHandler
	public void on(RemoveItemEvent itemRemoved) {
		// TODO Auto-generated method stub
		log.info("Item Removed eveent, {} ", itemRemoved);
		CartItem cartItem = itemRemoved.getCartItem();
		
		List<CartItem> cartItems = cartRepo.findByPrdIdAndUserIdAndProcessedAndActive(cartItem.getPrdId(), 	cartItem.getUserId(), false, true);

		for (CartItem cartItem2 : cartItems) {
			cartItem2.deActivee();
			cartRepo.save(cartItem2);
		}
	}
}
