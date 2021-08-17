package com.cart.core.event;

import com.cart.core.model.CartItem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCartEvent {

	private String id;
	private CartItem cartItem;
	
}
