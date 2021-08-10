package com.review.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class IntegrationConfig {

	@Autowired
	private AppProperties appProp;
	
	@Bean("product-service-int")
	public WebClient prdWebclient()
	{
		return WebClient.create(appProp.productServiceURL());
	}
	
	
}
