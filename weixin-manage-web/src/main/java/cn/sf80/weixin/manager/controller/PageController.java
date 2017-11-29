package cn.sf80.weixin.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/main")
    public String main(){
        return "main";
    }

    @GetMapping("/pc")
    public String phonecheck(){
        return "pc";
    }
}
