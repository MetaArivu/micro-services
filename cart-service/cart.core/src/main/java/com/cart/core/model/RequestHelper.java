package com.cart.core.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class RequestHelper {

	private String userId;
	private String userName;
	private String token;

	public void update(String user, String token) {
		this.userId = user;
		this.userName = user;
		this.token = token;
	}
	
	public String getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getToken() {
		return token;
	}
	
	
}
