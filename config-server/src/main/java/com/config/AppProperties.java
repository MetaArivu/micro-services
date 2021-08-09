package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppProperties {

	@Value("${app.info}")
	private String appInfo;

	@Value("${app.version}")
	private String appVersion;

	public String appInfo() {
		return this.appInfo + ", " + this.appVersion;
	}

}
