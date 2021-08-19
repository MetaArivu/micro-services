package com.order.adapter.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.config.AppProperties;
import com.order.domainlayer.service.ProductIntegrationService;
import com.order.dto.ProductDTO;
import com.order.dto.Response;
import com.order.exceptions.IntegrationException;
import com.order.exceptions.InvalidInputException;

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
	public Mono<List<ProductDTO>>  getProductDetails(List<String> prdIds) {
		
		
		String token = MDC.get("Authorization");
		//log.debug("Authorization="+token);
		String url = appProp.productServiceURL()+"/api/v1/products/";
		log.info("Fetching data from product service, url={}", url);
		return webclient
	            .build()
				.post()
				.uri(url)	
				.body(Mono.just(prdIds), List.class)
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
						List<ProductDTO> products = new ArrayList<ProductDTO>();
						String json = new ObjectMapper().writeValueAsString(resp.getData());
						List<LinkedHashMap> prds = new ObjectMapper().readValue(json, new ArrayList<LinkedHashMap>().getClass());
						for (LinkedHashMap prd : prds) {
							System.out.println("PRD="+prd);
							products.add(new ProductDTO(prd));
						}
						System.out.println(products);
						return Mono.just(products);
					} catch (JsonProcessingException e) {
						log.error("Exception {}",e.getMessage());
						e.printStackTrace();
						return Mono.error(new InvalidInputException(e.getMessage()));
					}
					
				});

	}
	 
}
