package com.t2m.g2nee.front.signup.controller;

import com.t2m.g2nee.front.signup.dto.MemberResponse;
import com.t2m.g2nee.front.signup.dto.SignupMemberRequestDto;
import com.t2m.g2nee.front.signup.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    MemberService memberService;

    public SignupController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String signup(Model model) {
        model.addAttribute("signupForm", new SignupMemberRequestDto());
        return "member/signupPage";
    }

    @PostMapping
    public String signupComplete(@ModelAttribute("signupForm") SignupMemberRequestDto request, Model model) {
        request.setIsOAuth(false);
        MemberResponse response = memberService.signup(request);

        model.addAttribute("memberInfo", response);
        return "member/signupComplete";
    }
}
