package com.imooc.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/frontend")
public class FrontendController {
    @GetMapping("/index")
    private String index(){
        return "frontend/index";
    }
    @GetMapping("/shoplist")
    private String shopList(){
        return "frontend/shoplist";
    }
    @GetMapping("/productdetail")
    private String productDetail(){
        return "frontend/productdetail";
    }
    @GetMapping("/shopdetail")
    private String shopDetail(){
        return "frontend/shopdetail";
    }
}
