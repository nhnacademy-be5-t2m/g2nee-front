package com.t2m.g2nee.front.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class adminController {


    @GetMapping
    public String adminMain() {
        return "admin/adminMain";
    }


}
