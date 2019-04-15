package com.xspace.alibaba.nacos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@EnableDiscoveryClient
@SpringBootApplication
public class AlibabaNacosDiscoveryClientWebclientApplication {

  public static void main(String[] args) {
    SpringApplication.run(AlibabaNacosDiscoveryClientWebclientApplication.class, args);
  }

  @Slf4j
  @RestController
  static class TestController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/test")
    public Mono<String> test() {
      Mono<String> result = webClientBuilder.build()
          .get()
          .uri("http://alibaba-nacos-discovery-server/hello?name=xue.zeng")
          .retrieve()
          .bodyToMono(String.class);
      return result;
    }
  }

  @Bean
  @LoadBalanced
  public WebClient.Builder loadBalancedWebClientBuilder() {
    return WebClient.builder();
  }
}
