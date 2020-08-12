package com.sunyard.consumer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class HelloController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    DiscoveryClient discoveryClient;
    @GetMapping("/hello")
    public String hello(){
        List<ServiceInstance> instances = discoveryClient.getInstances("provider");
        ServiceInstance serviceInstance = instances.get(0);
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("http://")
                .append(host)
                .append(":")
                .append(port)
                .append("/hello1");
        String s = restTemplate.getForObject(stringBuffer.toString(),String.class);
        return  s;
    }

    @Autowired
    @Qualifier("restTemplateOne")
    RestTemplate restTemplateOne;
    @GetMapping("/hello2")
    public String hello2(){
       return restTemplateOne.getForObject("http://provider/hello1",String.class);
    }
}
