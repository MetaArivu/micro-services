package com.cart.domainlayer.service;

import java.util.List;

import com.cart.core.exception.ServiceException;
import com.cart.core.model.CartItem;


public interface CartQueryService {

	public List<CartItem> userCart() throws ServiceException;
	
}
