package com.order.adapter.entities;

public class OrderItems {

	private String cartId;

	private String prdId;

	private String productName;

	private Double pricePerUnit;

	private Integer qty;

	private Double totalPrice;

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public String getPrdId() {
		return prdId;
	}

	public void setPrdId(String prdId) {
		this.prdId = prdId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void totalPrice() {
		this.totalPrice = 0.0;
		if (this.qty != null && this.pricePerUnit != null) {
			this.totalPrice = this.qty * this.pricePerUnit;
		}
		
	}

}
