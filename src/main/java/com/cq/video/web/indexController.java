package com.cq.video.web;

import org.springframework.web.bind.annotation.RequestMapping;

public class indexController {

    @RequestMapping(value = "/")
    public String index(){
        return "/index.html";
    }

}
