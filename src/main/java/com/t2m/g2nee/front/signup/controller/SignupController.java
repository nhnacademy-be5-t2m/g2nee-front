package com.t2m.g2nee.front.signup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    @GetMapping
    public String signup() {
        return "member/signupPage";
    }

    @PostMapping
    public String signupComplate() {

        return "member/signupComplate";
    }
}
