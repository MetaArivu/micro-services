package com.order.event;

import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaymentEvent {

	private String paymentId;
	private String referenceId;
	private Date date;
	private boolean success;
	private String remarks;

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public static final PaymentEvent getInstance(String str, ObjectMapper objMapper) {
		try {
			return objMapper.readValue(str, PaymentEvent.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
