package com.t2m.g2nee.front.member.controller;

import com.t2m.g2nee.front.member.service.MemberService;
import com.t2m.g2nee.front.order.dto.request.CustomerOrderCheckRequestDto;
import com.t2m.g2nee.front.order.dto.response.OrderInfoDto;
import com.t2m.g2nee.front.order.service.OrderGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberRestController {
    private final MemberService memberService;
    private final OrderGetService orderGetService;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * username의 중복여부를 체크하는 메소드
     *
     * @param username 중복여부를 체크할 username
     * @return 중복여부를 true, false 로 반환
     */
    @PostMapping("/existsUsername")
    public ResponseEntity<Boolean> existsUsername(@RequestBody String username) {
        Boolean result = memberService.existsUsername(username);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    /**
     * nickname의 중복여부를 체크하는 메소드
     *
     * @param nickname 중복여부를 체크할 nickname
     * @return 중복여부를 true, false 로 반환
     */
    @PostMapping("/existsNickname")
    public ResponseEntity<Boolean> existsNickname(@RequestBody String nickname) {
        Boolean result = memberService.existsNickName(nickname);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    /**
     * 비회원 주문번호와 비밀번호가 일치하는지 확인
     *
     * @param request 비회원주문번호와 비밀번호가 담긴 dto
     * @return 일치여부를 true false로 반환
     */
    @PostMapping("/nonMemberOrderCheck")
    public ResponseEntity<String> nonMemberOrderCheck(@RequestBody CustomerOrderCheckRequestDto request) {
        if(!orderGetService.existsOrder(request.getOrderId())) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("잘못된 주문번호입니다.");
        }
        OrderInfoDto.Response orderResponse = orderGetService.getOrderByNumber(request.getOrderId());
        String password = memberService.getCustomerPassword(orderResponse.getCustomerId());
        String result;
        if(password.equals("NULL")){
            result = "잘못된 주문정보입니다.";
        }else if(password.equals("MEMBER")){
            result = "회원 주문입니다. 로그인 후 확인해주세요.";
        }else if(passwordEncoder.matches(request.getPassword(),password)){
            result = "SUCCESS";
        }else{
            result = "비밀번호가 일치하지 않습니다.";
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }
}
