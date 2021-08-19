package com.order;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class IntegrationConfig {

	@Bean()
	@LoadBalanced
	public WebClient.Builder prdWebclient()
	{
		return WebClient.builder();
	}
	
	
}
