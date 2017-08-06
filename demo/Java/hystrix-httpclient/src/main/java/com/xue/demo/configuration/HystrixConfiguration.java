package com.xue.demo.configuration;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
class HystrixConfiguration {

  //注册Hystrix框架的拦截器，使得框架的注解能够生效
  @Bean
  public HystrixCommandAspect hystrixAspect() {
    return new HystrixCommandAspect();
  }

  /**
   * to expose stream endpoint
   * 注入servlet,拦截url="/hystrix.stream"
   */
  @Bean
  public ServletRegistrationBean servletRegistrationBean() {
    return new ServletRegistrationBean(new HystrixMetricsStreamServlet(), "/hystrix.stream");
  }

}