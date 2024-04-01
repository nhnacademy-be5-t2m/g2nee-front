package com.t2m.g2nee.front.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping
    public String main(){
        return "templates/main/index.html";
    }
}
