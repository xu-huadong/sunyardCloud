package com.sunyard.provider;

import com.sunyard.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterController {
    @PostMapping("/register")
    public String register(User user){
        return "redirect:http://provider/loginPager?userName="+user.getUsername();
    }
    @GetMapping("/loginPager")
    @ResponseBody
    public String loginPager(String userName){
        return "loginPager:"+userName;
    }
}
