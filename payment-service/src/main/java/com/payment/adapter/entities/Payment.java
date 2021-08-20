package com.payment.adapter.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document(value = "payment")
@JsonInclude(value = Include.NON_NULL)
public class Payment extends BaseEntity {

	private CardDetails card;
	private Double totalAmount;
	private boolean success;
	private String referenceId;
	private Date date;
	private String remark;

	public Payment() {
		this.active = true;
		this.date = new Date();
	}
	
	public Payment(String id, String referenceId, boolean success, String remark) {
		this();
		this.id = id;
		this.referenceId = referenceId;
		this.success = success;
		this.remark = remark;
	}
	
	public CardDetails getCard() {
		return card;
	}

	public void setCard(CardDetails card) {
		this.card = card;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		return id;
	}
}
