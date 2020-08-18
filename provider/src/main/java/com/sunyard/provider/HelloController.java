package com.sunyard.provider;

import com.sunyard.bean.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Value("${server.port}")
    Integer port;
    @GetMapping("/hello1")
    public String hello(){
        return "hello springCloud"+port;
    }

    @GetMapping("/restemplate")
    public String helloRestemplate(String name){
        return "this is restemolate"+ name ;
    }

    @PostMapping("/addUser1")
    public User addUser(User user){
        return user;
    }

    @PostMapping("/addUser2")
    public User addUser2(@RequestBody User user){
        return user;
    }

}
