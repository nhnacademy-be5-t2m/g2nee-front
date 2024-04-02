package com.t2m.g2nee.front.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping
    public String main() {
        return "main/index";
    }

    @GetMapping("/shop")
    public String shop() {
        return "main/shop";
    }

    @GetMapping("/shopDetail")
    public String detail() {
        return "main/detail";
    }

    @GetMapping("/cart")
    public String cart() {
        return "main/cart";
    }
}
