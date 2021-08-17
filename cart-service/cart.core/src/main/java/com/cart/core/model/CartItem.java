package com.cart.core.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Document(collection =  "cartitems")
public class CartItem {

	@Id
	private String id;
	
	private String aggrId;
	
	private String prdId;
	
	private Integer qty;
	
	private String userId;
	
	private boolean processed;

	private Date createDate;

	private Date updateDate;
	
	private boolean active;

	
	public CartItem cloneAllWithDefault() {
		return CartItem.builder()
			.prdId(this.getPrdId())
			.qty(this.getQty())
			.userId("1234")
			.createDate(new Date())
			.processed(false)
			.active(true)
			.build();
			
	}
	
	public CartItem cloneAndDeactivate() {
		return CartItem.builder()
			.prdId(this.getPrdId())
			.qty(0)
			.userId("1234")
			.createDate(new Date())
			.processed(false)
			.active(false)
			.build();
			
	}
	
	public CartItem init() {
		return CartItem.builder()
			.userId("1234")
			.createDate(new Date())
			.processed(false)
			.active(true)
			.build();
			
	}
	
	public void deActivee() {
		this.active = false;
		this.updateDate = new Date();
	}
	
	
}
