package com.cart.command;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.cart.core.model.CartItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AddItemCommand {

	@TargetAggregateIdentifier
	private String id;

	private CartItem cartItem;

	public AddItemCommand cloneWithDefault() {
		return AddItemCommand
				.builder()
				.id(UUID.randomUUID().toString())
				.cartItem(this.getCartItem().cloneAllWithDefault())
				.build();
	}
}
