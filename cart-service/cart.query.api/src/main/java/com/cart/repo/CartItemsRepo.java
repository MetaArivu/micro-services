package com.cart.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cart.core.model.CartItem;

@Repository
public interface CartItemsRepo extends MongoRepository<CartItem, String> {

	public List<CartItem> findByPrdIdAndUserIdAndProcessedAndActive(String prdId, String userId, boolean processed, boolean active);
	
	
	public List<CartItem> findByUserIdAndProcessedAndActive(String userId, boolean processed, boolean active);

}
