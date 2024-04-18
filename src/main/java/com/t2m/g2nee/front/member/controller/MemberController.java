package com.t2m.g2nee.front.member.controller;

import com.t2m.g2nee.front.member.dto.request.MemberLoginRequestDto;
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
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 회원과 관련된 controller 입니다.
 *
 * @author : 정지은
 * @since : 1.0
 */
@Controller
@RequestMapping("/member")
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
        String passwordEncoding = passwordEncoder.encode(request.getPassword());
        request.setPassword(passwordEncoding);
        MemberResponse response = memberService.signup(request);

        model.addAttribute("memberInfo", response);
        return "member/signupComplete";
    }

    /**
     * 회원 로그인을 위한 페이지
     *
     * @param model 모델
     * @return 회원 일반 로그인 페이지
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("memberLoginForm", new MemberLoginRequestDto());
        return "member/login";
    }

    /**
     * 회원 일반 로그인 정보로 통신한 후 성공페이지를 띄워주는 메소드.
     *
     * @param request 회원의 아이디 비밀번호가 담긴 요청
     * @param model   회원 정보 일치하는지 저장할 model
     * @return 성공, 실패 페이지를 보여준다.
     */
<<<<<<< HEAD
    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        memberService.logout(response);
        return "redirect:/";
=======
    @PostMapping("/login")
    public String memberLoginComplete(@ModelAttribute("memberLoginForm") MemberLoginRequestDto request,
                                      Model model) {
        Boolean loginSuccess = memberService.login(request);

        if (loginSuccess) {
            return "redirect:/main/index";
        } else {
            model.addAttribute("failCustomerLogin", "비회원 정보가 일치하지 않습니다.");
            return "member/login";
        }
>>>>>>> parent of 7c899e1 (:sparkles: accesstoken 응답 후 저장)
    }


}
