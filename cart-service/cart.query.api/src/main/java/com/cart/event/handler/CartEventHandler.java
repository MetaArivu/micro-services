package com.cart.event.handler;

import com.cart.core.event.ItemAddedEvent;
import com.cart.core.event.RemoveItemEvent;

public interface CartEventHandler {

	public void on(ItemAddedEvent itemAdded);
	
	public void on(RemoveItemEvent itemRemoved);
}
