package com.sunyard.consumer;


import com.sunyard.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

    @GetMapping("/hello3")
    public String helloe(){
        String s2 = restTemplateOne.getForObject("http://provider/restemplate?name={name}", String.class,"javaboy");
        System.out.println(s2);
        ResponseEntity<String> responseEntity = restTemplateOne.getForEntity("http://provider/restemplate?name={name}", String.class, "javagirl");
        String body = responseEntity.getBody();
        System.out.println("body:"+body);
        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println("statusCode:"+statusCode);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        System.out.println("statusCodeValue:"+statusCodeValue);
        HttpHeaders headers = responseEntity.getHeaders();
        Set<String> strings = headers.keySet();
        for (String string : strings) {
            System.out.println(string+":"+headers.get(string));
        }
        return s2;
    }
    @GetMapping("/hello4")
    public void helllo4() throws UnsupportedEncodingException {
        //第一种方式
        String s1 = restTemplate.getForObject("http://provider/restemplate?name={1}", String.class, "javaboy");
        System.out.println(s1);
        //第二种方式
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","javaboy");
        String s2 = restTemplate.getForObject("http://provider/restemplate?name={name}", String.class, map);
        System.out.println(s2);
        //第三种方式
        String uriString = "http://provider/restemplate?name="+ URLEncoder.encode("张三","UTF-8");
        URI uri = URI.create(uriString);
        String s3 = restTemplate.getForObject(uri, String.class);
        System.out.println(s3);

    }

   /* post请求*/
    @GetMapping("/hello5")
    public void hello5(){
        LinkedMultiValueMap<Object, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("username","javaboy");
        multiValueMap.add("age",18);
        User user = restTemplate.postForObject("http://provider/addUser1", multiValueMap, User.class);
        System.out.println(user);
        user.setUsername("javagirl");
        User user1 = restTemplate.postForObject("http://provider/addUser2", user, User.class);
        System.out.println(user1);
    }

    /*重定向*/
    @GetMapping("/hello6")
    public void hello6(){
        LinkedMultiValueMap<Object, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("username","javaboy");
        multiValueMap.add("age",18);
        URI uri = restTemplate.postForLocation("http://provider/register", multiValueMap);
        String s = restTemplate.getForObject(uri, String.class);
        System.out.println(s);
    }

    //put请求
    @GetMapping("/hello7")
    public void hello7(){
        LinkedMultiValueMap<Object, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("username","javaboy");
        multiValueMap.add("age",18);
        restTemplate.put("http://provider/updateUser",multiValueMap);
        User user = new User();
        user.setUsername("xuhuadong");
        user.setAge(28);
        restTemplate.put("http://provider/updateUser1",user);

    }

    @GetMapping("hello8")
    public void hello8(){
        restTemplate.delete("http://provider/deleteUser?id={1}",18);
        restTemplate.delete("http://provider/deleteUser1/{1}",19);
    }







}
