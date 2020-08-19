package com.sunyard.provider;

import com.sunyard.bean.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/updateUser")
    public void  updateUser(User user){
        System.out.println(user);
    }

    @PutMapping("/updateUser1")
    public void updateUser1(@RequestBody User user){
        System.out.println(user);
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(Integer id){
        System.out.println("id:"+id);
    }

    @DeleteMapping("/deleteUser1/{id}")
    public void deleteUser1(@PathVariable Integer id){
        System.out.println("id:"+id);
    }




}
