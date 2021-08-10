package com.review.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@org.springframework.context.annotation.Configuration
@RefreshScope
public class AppProperties {

	@Value("${app.info}")
	private String appInfo;

	@Value("${app.version}")
	private String appVersion;

	@Value("${app.public_routes}")
	private String[] publicAPI;

	@Value("${jwt.secret}")
	private String jwtSecret;

	public String[] getPublicAPI() {
		return this.publicAPI;
	}

	public String getJwtSecret() {
		return this.jwtSecret;
	}
	

	public String appInfo() {
		return this.appInfo + ", " + this.appVersion;
	}

}
