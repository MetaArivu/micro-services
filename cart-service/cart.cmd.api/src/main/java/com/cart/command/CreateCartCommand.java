package com.cart.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.cart.core.model.CartItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateCartCommand {

	@TargetAggregateIdentifier
	private String id;
	
	private CartItem cartItem;
	
	
}
