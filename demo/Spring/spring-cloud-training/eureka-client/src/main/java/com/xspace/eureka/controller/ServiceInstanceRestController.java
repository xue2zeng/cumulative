package com.xspace.eureka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceInstanceRestController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/service-instances")
    public String getServiceInstances() {
        String services = "Services: " + this.discoveryClient.getServices();
        return services;
    }
}
