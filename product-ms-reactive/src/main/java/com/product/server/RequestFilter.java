package com.product.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class RequestFilter implements WebFilter {

	private static final Logger log = (Logger) LoggerFactory.getLogger(RequestFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		long startTime = System.currentTimeMillis();
		ServerHttpRequest request = exchange.getRequest();
		RequestPath path = request.getPath();

		return chain.filter(exchange).doFinally(signalType -> {
			long totalTime = System.currentTimeMillis() - startTime;
			log.info("IP={}, Path={}, TT= {}", request.getRemoteAddress().toString(), path.toString(), totalTime);
		});
	}

}
