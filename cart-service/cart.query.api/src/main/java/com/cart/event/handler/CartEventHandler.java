package com.cart.event.handler;

import com.cart.core.event.ItemAddedEvent;

public interface CartEventHandler {

	public void on(ItemAddedEvent itemAdded);
}
