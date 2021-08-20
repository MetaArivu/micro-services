package com.order.adapter.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document(value = "orders")
@JsonInclude(value = Include.NON_NULL)
public class Order extends BaseEntity {

	private Date orderDate;
	private String userId;
	private Address address;
	private List<OrderItems> items;
	private String note;
	private Double totalAmount;
	private boolean placed;
	private boolean payment;
	private long orderNo;

	public Order() {
		super();
		this.active = true;
	}
	
	public Order(String orderId, long orderNo) {
		this.id = orderId;
		this.orderNo = orderNo;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<OrderItems> getItems() {
		return items;
	}

	public void setItems(List<OrderItems> items) {
		this.items = items;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public boolean isPlaced() {
		return placed;
	}

	public void setPlaced(boolean placed) {
		this.placed = placed;
	}

	public boolean isPayment() {
		return payment;
	}

	public void setPayment(boolean payment) {
		this.payment = payment;
	}

	public long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}

	public void updateData(String userId) {
		this.userId = userId;
		this.orderNo = System.currentTimeMillis();
		this.orderDate = new Date();
		this.totalAmount = 0.0;
		for (OrderItems orderItems : items) {
			orderItems.totalPrice();
			this.totalAmount = this.totalAmount + orderItems.getTotalPrice();
		}
	}

	public List<String> productIds() {
		List<String> productIds = new ArrayList<String>();
		for (OrderItems orderItems : items) {
			productIds.add(orderItems.getPrdId());
		}
		return productIds;
	}

	@JsonIgnore
	@Override
	public boolean isValid() {
		return true;
	}

	public static String invalidMsg() {
		return "Please enter valid order data";
	}

	@Override
	public String toString() {
		return id + "|" + userId;
	}
}
