package com.cart;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.core.exception.ServiceException;
import com.cart.core.model.CartItem;
import com.cart.domainlayer.service.CartQueryService;
import com.cart.repo.CartItemsRepo;

@Service
public class CartQueryServiceImpl implements CartQueryService {

	private static final Logger log = LoggerFactory.getLogger(CartQueryServiceImpl.class);

	@Autowired
	private CartItemsRepo cartRepo;

	@Override
	public List<CartItem> userCart() throws ServiceException {
		List<CartItem> userCart = cartRepo.findByUserIdAndProcessedAndActive("1234", false, true);
		if (userCart.size() == 0) {
			throw new ServiceException("User cart is empty");
		}
		log.info("User cart size={}", userCart.size());
		return userCart;
	}

}
