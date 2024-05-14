package com.t2m.g2nee.front.member.controller;

import com.t2m.g2nee.front.member.dto.request.SignupMemberRequestDto;
import com.t2m.g2nee.front.member.dto.response.MemberResponse;
import com.t2m.g2nee.front.member.service.MemberService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 회원과 관련된 controller 입니다.
 *
 * @author : 정지은
 * @since : 1.0
 */
@Controller
public class MemberController {

    MemberService memberService;
    BCryptPasswordEncoder passwordEncoder;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    /**
     * 기본 회원가입 페이지
     *
     * @param model 모델
     * @return 기본 회원가입 페이지
     */
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("signupForm", new SignupMemberRequestDto());
        return "member/signupPage";
    }

    /**
     * 회원가입 정보로 통신한 후 성공페이지를 띄워주는 메소드.
     *
     * @param request 회원가입 정보를 담고있다.
     * @param model   회원가입을 성공한 회원의 기본정보를 담기 위한 model
     * @return 성공, 실패 페이지를 보여준다.
     */
    @PostMapping("/signup")
    public String signupComplete(@ModelAttribute("signupForm") SignupMemberRequestDto request, Model model) {
        request.setIsOAuth(false);
        request.setGender(request.getGender().substring(0, request.getGender().length() - 1));
        String passwordEncoding = passwordEncoder.encode(request.getPassword());
        request.setPassword(passwordEncoding);
        MemberResponse response = memberService.signup(request);

        model.addAttribute("memberInfo", response);
        return "member/signupComplete";
    }

    /**
     * 회원 로그인을 위한 페이지
     *
     * @return 회원 일반 로그인 페이지
     */
    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    /**
     * 회원 로그아웃을 위한 페이지
     *
     * @return 기본 index 페이지
     */
    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        memberService.logout(response);
        return "redirect:/";
    }

}
