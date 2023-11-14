package com.sadana.tech.filter;

import java.nio.charset.StandardCharsets;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class CustomGlobalFilter {

	@Bean
	@Order(-1)
	public GlobalFilter globalFilter() {
		return (exchange, chain) -> {
			log.info("first pre filter"+ this.resolveBodyFromRequest(exchange.getRequest()));
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {				
			}));
		};
	}
	
	private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest){
	    Flux<DataBuffer> body = serverHttpRequest.getBody();
	    StringBuilder sb = new StringBuilder();

	    body.subscribe(buffer -> {
	        byte[] bytes = new byte[buffer.readableByteCount()];
	        buffer.read(bytes);
	        DataBufferUtils.release(buffer);
	        String bodyString = new String(bytes, StandardCharsets.UTF_8);
	        sb.append(bodyString);
	    });
	    return sb.toString();

	}

}
