package com.sunyard.consul_consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;


import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloControllrt {
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    RestTemplate restTemplate;
    @GetMapping("/hello")
    public String hello(){
        ServiceInstance consul_provider = loadBalancerClient.choose("consul-provider");
        System.out.println(
                "服务地址:"+consul_provider.getUri()
        );
        System.out.println(
                "服务名称:"+consul_provider.getServiceId()
        );
        String forObject = restTemplate.getForObject(consul_provider.getUri() + "/helloConsul", String.class);
        return forObject;
    }
}
