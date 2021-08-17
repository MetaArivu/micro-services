package com.cart.aggregate;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cart.core.model.CartItem;

@Repository
public interface CartItemRepo extends MongoRepository<CartItem, String> {

	
	public List<CartItem> findByUserIdAndProcessedAndActive(String userId, boolean processed, boolean active);
}
