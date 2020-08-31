package com.sunyard.consul_provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloConsulController {
    @Value("${server.port}")
    Integer port;
    @GetMapping("/helloConsul")
    public Integer  hello(){
        return port;
    }
}
