package com.cart.adapter;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.cart.core.config.AppProperties;
import com.cart.domainlayer.service.ProductIntegrationService;
import com.cart.dto.ProductDTO;
import com.cart.dto.Response;
import com.cart.exceptions.IntegrationException;
import com.cart.exceptions.InvalidInputException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class ProductIntegrationServiceImpl implements ProductIntegrationService{

	private static final Logger log = (Logger) LoggerFactory.getLogger(ProductIntegrationServiceImpl.class);

	@Autowired
	private AppProperties appProp;
	
	@Autowired()
	private WebClient.Builder webclient;

	
	
	@Override
	public ProductDTO  getProductDetails(String id) {
		
		
		String token = MDC.get("Authorization");
		log.debug("Authorization="+token);
		String url = appProp.productServiceURL()+"/api/v1/" + id;
		log.info("Fetching data from product service, url={}", url);
		return webclient
	            .build()
				.get()
				.uri(url)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization",token)
				.retrieve()
				.onStatus(HttpStatus::is5xxServerError, 
				          response -> Mono.error(new IntegrationException(response.toString(), response.rawStatusCode())))
				.bodyToMono(Response.class)
				.retryWhen(Retry.backoff(2, Duration.ofSeconds(2))
				          .filter(throwable -> {
				        	  return throwable instanceof IntegrationException;
				          }))
				.doOnError(e ->{
					 log.error("Exception="+e.getMessage());
					 Mono.error(new IntegrationException("Unable to fetch product details, please try after sometime", 0));
				})
				.onErrorMap(e ->{
					 log.error("ErrorMap="+e.getMessage());
					 return new IntegrationException("Unable to fetch product details, please try after sometime");
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
					
				}).block();

	}
	
	 
}
