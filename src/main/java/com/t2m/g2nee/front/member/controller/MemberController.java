package com.t2m.g2nee.front.member.controller;

import com.t2m.g2nee.front.member.dto.SignupRequestDto;
import com.t2m.g2nee.front.member.dto.SignupResponseDto;
import com.t2m.g2nee.front.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class MemberController {

    MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String signup(Model model) {
        model.addAttribute("signupForm", new SignupRequestDto());
        return "member/signupPage";
    }

    @PostMapping
    public String signup(@ModelAttribute("signupForm") SignupRequestDto request, Model model) {
        request.setIsOAuth(false);
        SignupResponseDto response = memberService.signup(request);

        model.addAttribute("memberInfo", response);
        return "member/signupComplete";
    }
}
