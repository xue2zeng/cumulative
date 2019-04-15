package com.xspace.alibaba.nacos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class AlibabaNacosConfigClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(AlibabaNacosConfigClientApplication.class, args);
  }

  @Slf4j
  @RestController
  @RefreshScope
  static class TestController {

    @Value("${xspace.title:}")
    private String title;

    @GetMapping("/test")
    public String hello() {
      return title;
    }

  }
}
