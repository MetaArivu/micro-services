package com.cart.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cart.command.AddItemCommand;
import com.cart.command.CreateCartCommand;
import com.cart.core.event.CreateCartEvent;
import com.cart.core.event.ItemAddedEvent;
import com.cart.core.model.CartItem;

@Aggregate
public class CartAggregate {

	private static final Logger log = LoggerFactory.getLogger(CartAggregate.class);
	
	@AggregateIdentifier
	private String id;
	
	private CartItem cartItem;
	
	public CartAggregate() {
		
	}
	
	@CommandHandler
	public CartAggregate(CreateCartCommand command) {
		
		log.info("Create Card Command Received, id={} ",command.getId());

		CreateCartEvent event = CreateCartEvent.builder()
				.id(command.getId())
				.cartItem(command.getCartItem())
				.build();
		
		AggregateLifecycle.apply(event);
	}
	
	@EventSourcingHandler
	public void on(CreateCartEvent event) {
		log.info("Create Cart Event, id={}",event.getId());
		this.id = event.getId();
		this.cartItem = event.getCartItem();
	}
	
	@CommandHandler
	public void handle(AddItemCommand command) {
		
		log.info("Add Item Command Received, id={} ",command.getId());
		CartItem cartItem = command.getCartItem();
		ItemAddedEvent event = ItemAddedEvent.builder()
				.id(command.getId())
				.cartItem(cartItem)
				.build();
		
		AggregateLifecycle.apply(event);
	}
	
	@EventSourcingHandler
	public void on(ItemAddedEvent event) {
		log.info("Item Added Event, id={}",event.getId());
		this.id = event.getId();
		this.cartItem = event.getCartItem();
	}
}
