package com.t2m.g2nee.front.signup.controller;

import com.t2m.g2nee.front.signup.dto.MemberResponse;
import com.t2m.g2nee.front.signup.dto.SignupMemberRequestDto;
import com.t2m.g2nee.front.signup.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 회원가입에 필요한 controller
 *
 * @author : 정지은
 * @since : 1.0
 */
@Controller
@RequestMapping("/signup")
public class SignupController {
    MemberService memberService;

    public SignupController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String signup() {
        return "/member/signUpPage";
    }

    @PostMapping
    public String signupComplete(SignupMemberRequestDto request, Model model) {
        request.setIsOAuth(false);
        MemberResponse response = memberService.signup(request);

        model.addAttribute("memberInfo", response);
        return "member/signupComplete";
    }
}
