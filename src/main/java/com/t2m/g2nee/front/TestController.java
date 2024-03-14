package com.t2m.g2nee.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String hello(){

        return "Hello Test!";
    }
}