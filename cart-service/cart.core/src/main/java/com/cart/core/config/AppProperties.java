package com.cart.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppProperties {

	
	@Value("${spring.data.mongodb.host}")
	private String dbHost;

	@Value("${spring.data.mongodb.port}")
	private int dbPort;

	@Value("${spring.data.mongodb.database}")
	private String dbName;
	
	
	@Value("${app.info}")
	private String appInfo;

	@Value("${app.version}")
	private String appVersion;

	@Value("${app.public_routes}")
	private String[] publicAPI;

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${url.product}")
	private String prdSvcUrl;

	public String[] getPublicAPI() {
		return this.publicAPI;
	}

	public String getJwtSecret() {
		return this.jwtSecret;
	}
	

	public String appInfo() {
		return this.appInfo + ", " + this.appVersion;
	}

	public String productServiceURL() {
		return this.prdSvcUrl;
	}

	public String getDbHost() {
		return dbHost;
	}

	public int getDbPort() {
		return dbPort;
	}

	public String getDbName() {
		return dbName;
	}
	
	
}
