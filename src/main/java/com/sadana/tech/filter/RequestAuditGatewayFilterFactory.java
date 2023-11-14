package com.sadana.tech.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RequestAuditGatewayFilterFactory extends AbstractGatewayFilterFactory<RequestAuditGatewayFilterFactory.Config> {
	
	public RequestAuditGatewayFilterFactory() {
		super(Config.class);
	}

	
	public static class Config {
		
	}

	@Override
	public GatewayFilter apply(Config config) {	
		return (exchange,chain) ->{
			Object cachedBody = exchange.getAttribute(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR);
			log.info("Request:::::" + cachedBody.toString());				
			return chain.filter(exchange);
		};
	}
	
	
	
}
