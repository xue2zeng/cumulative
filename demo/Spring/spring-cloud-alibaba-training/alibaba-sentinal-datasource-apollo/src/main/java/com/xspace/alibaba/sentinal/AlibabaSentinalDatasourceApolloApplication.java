package com.xspace.alibaba.sentinal;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableApolloConfig
@SpringBootApplication
public class AlibabaSentinalDatasourceApolloApplication {

  public static void main(String[] args) {
    SpringApplication.run(AlibabaSentinalDatasourceApolloApplication.class, args);
  }

  @Slf4j
  @RestController
  static class TestController {

    @GetMapping("/hello")
    public String hello() {
      return "xspace.com";
    }

  }
}
