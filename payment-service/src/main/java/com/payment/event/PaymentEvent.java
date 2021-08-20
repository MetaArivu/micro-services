package com.payment.event;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentEvent {

	private String paymentId;
	private String referenceId;
	private Date date;
	private boolean success;
	private String remarks;
	
	
}
