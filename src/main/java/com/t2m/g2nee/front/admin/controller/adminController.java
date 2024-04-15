package com.t2m.g2nee.front.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class adminController {

    @GetMapping("/admin")
    public String adminMain() {
        return "admin/adminMain";
    }

    @GetMapping("/admin/membermanagement/")
    public String adminOrder(){
        return "admin/adminOrder";
    }
}
