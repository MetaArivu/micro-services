package com.review.adapter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.review.config.AppProperties;
import com.review.domainlayer.service.ProductIntegrationService;
import com.review.dto.ProductDTO;
import com.review.dto.Response;
import com.review.exceptions.InvalidInputException;

import reactor.core.publisher.Mono;

@Service
public class ProductIntegrationServiceImpl implements ProductIntegrationService{

	private static final Logger log = (Logger) LoggerFactory.getLogger(ProductIntegrationServiceImpl.class);

	@Autowired
	private AppProperties appProp;
	
	@Autowired()
	private WebClient.Builder webclient;

	
	@Override
	public Mono<ProductDTO>  getProductDetails(String id) {
		
		
		String token = MDC.get("Authorization");
		//log.debug("Authorization="+token);
		String url = appProp.productServiceURL()+"/api/v1/" + id;
		log.info("Fetching data from product service, url={}", url);
		return webclient
	            .build()
				.get()
				.uri(url)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization",token)
				.exchange()
				.flatMap(response -> {
					log.info("Response received");
					return response.bodyToMono(Response.class);
				})
				.filter((resp)-> resp!=null && resp.isSuccess())
				.flatMap((resp)->{
					
					try {
						String json = new ObjectMapper().writeValueAsString(resp.getData());
						ProductDTO prd = new ObjectMapper().readValue(json, ProductDTO.class);
						return Mono.just(prd);
					} catch (JsonProcessingException e) {
						log.error("Exception {}",e.getMessage());
						e.printStackTrace();
						return Mono.error(new InvalidInputException(e.getMessage()));
					}
					
				});

	}
	
	// This method returns filter function which will log request data
    private ExchangeFilterFunction logRequest() {
        
    	return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            
        	log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.debug("{}={}", name, value)));
            
            return Mono.just(clientRequest);
        });
    	
    }
    
    private ExchangeFilterFunction logResponse() {
	    
    	return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
	        log.debug("Response Status: {}", clientResponse.statusCode());
	        return Mono.just(clientResponse);
	    });
    	
	}
}
