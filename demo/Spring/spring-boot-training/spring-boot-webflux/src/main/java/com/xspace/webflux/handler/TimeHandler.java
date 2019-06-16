package com.xspace.webflux.handler;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.time.Duration;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TimeHandler {
  public Mono<ServerResponse> getTime(ServerRequest serverRequest) {
    return ok().contentType(MediaType.TEXT_PLAIN)
        .body(Mono.just("Now is " + DateFormatUtils.format(new Date(), "HH:mm:ss")), String.class);
  }

  public Mono<ServerResponse> getDate(ServerRequest serverRequest) {
    return ok().contentType(MediaType.TEXT_PLAIN)
        .body(
            Mono.just("Today is " + DateFormatUtils.format(new Date(), "yyyy-MM-dd")),
            String.class);
  }

  public Mono<ServerResponse> sendTimePerSec(ServerRequest serverRequest) {
		return ok().contentType(MediaType.TEXT_EVENT_STREAM).body(
				Flux.interval(Duration.ofSeconds(1)).
						map(l -> DateFormatUtils.format(new Date(), "HH:mm:ss")), String.class);
	}
}
