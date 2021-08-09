package com.user.config;

import org.springframework.beans.factory.annotation.Value;

@org.springframework.context.annotation.Configuration
public class AppProperties {

	@Value("${app.public_routes}")
	private String[] publicAPI;

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expiration}")
	private Long jwtExpiration;

	public String[] getPublicAPI() {
		return this.publicAPI;
	}

	public String getJwtSecret() {
		return this.jwtSecret;
	}

	public Long getJwtExpiration() {
		return this.jwtExpiration;
	}

}
