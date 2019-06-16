package com.xspace.webflux.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.xspace.webflux.handler.TimeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

	@Autowired
	private TimeHandler timeHandler;

	@Bean
	public RouterFunction<ServerResponse> timerRouter() {
		return route(GET("/time"), req -> timeHandler.getTime(req))
				// 这种方式相对于上一行更加简洁
				.andRoute(GET("/date"), timeHandler::getDate)
				.andRoute(GET("/times"), timeHandler::sendTimePerSec);
	}
}
