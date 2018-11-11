package com.xspace.eureka.feign.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("eureka-client")
public interface ServiceClient {

    @GetMapping("/service-instances")
    String consumer();
}
