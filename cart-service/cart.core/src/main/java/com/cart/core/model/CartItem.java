package com.cart.core.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Document(collection =  "cartitems")
@JsonInclude(value = Include.NON_NULL)
public class CartItem {

	@Id
	private String id;
	
	@JsonIgnore
	private String aggrId;
	
	private String prdId;
	
	private String productName;
	
	private Double pricePerUnit;
	
	private Integer qty;
	
	private Double totalPrice;
	
	@JsonIgnore
	private String userId;
	
	@JsonIgnore
	private boolean processed;

	@JsonIgnore
	private Date createDate;

	@JsonIgnore
	private Date updateDate;
	
	@JsonIgnore
	private boolean active;

	
	public CartItem cloneAllWithDefault(String user) {
		return CartItem.builder()
			.prdId(this.getPrdId())
			.productName(this.getProductName())
			.pricePerUnit(this.getPricePerUnit())
			.qty(this.getQty())
			.totalPrice(this.getPricePerUnit()*this.getQty())
			.userId(user)
			.createDate(new Date())
			.processed(false)
			.active(true)
			.build();
			
	}
	
	public CartItem cloneAndDeactivate(String user) {
		return CartItem.builder()
			.prdId(this.getPrdId())
			.qty(0)
			.userId(user)
			.createDate(new Date())
			.processed(false)
			.active(false)
			.build();
			
	}
	
	public CartItem init(String user) {
		return CartItem.builder()
			.userId(user)
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
