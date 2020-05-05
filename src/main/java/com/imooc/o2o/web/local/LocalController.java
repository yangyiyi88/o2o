package com.imooc.o2o.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/local")
public class LocalController {
    @RequestMapping("/login")
    public String login(){
        return "local/login";
    }

    @RequestMapping("/changepsw")
    public String changePsw(){
        return "local/changepsw";
    }
}
