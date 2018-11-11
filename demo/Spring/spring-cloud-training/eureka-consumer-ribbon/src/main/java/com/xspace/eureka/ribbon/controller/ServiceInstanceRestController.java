package com.xspace.eureka.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ServiceInstanceRestController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/service-instances")
    public String getServiceInstances() {
        return restTemplate.getForObject("http://eureka-client/service-instances", String.class);
    }
}
