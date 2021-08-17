package com.cart.domainlayer.service;

import com.cart.command.AddItemCommand;
import com.cart.command.RemoveItemCommand;
import com.cart.core.exception.ServiceException;

public interface CartService {

	public void handle(AddItemCommand addItemCommand) throws ServiceException;
	
	public void handle(RemoveItemCommand removeItemCommand) throws ServiceException;
	
}
