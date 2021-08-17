package com.cart.domainlayer.service;

import com.cart.command.AddItemCommand;
import com.cart.core.exception.ServiceException;

public interface CartService {

	public void handle(AddItemCommand addItemCommand) throws ServiceException;
	
	
}
