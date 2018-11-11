package com.xspace.eureka.feign.controller;

import com.xspace.eureka.feign.proxy.ServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceInstanceRestController {
    @Autowired
    ServiceClient serviceClient;

    @GetMapping("/service-instances")
    public String getServiceInstances() {
        return serviceClient.consumer();
    }
}
