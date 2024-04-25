package com.t2m.g2nee.front.member.controller;

import com.t2m.g2nee.front.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberRestController {
    MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * username의 중복여부를 체크하는 메소드
     *
     * @param username 중복여부를 체크할 username
     * @return 중복여부를 true, false 로 반환
     */
    @PostMapping("/existsUsername")
    public ResponseEntity<Boolean> existsUsername(@RequestBody String username){
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
    public ResponseEntity<Boolean> existsNickname(@RequestBody String nickname){
        Boolean result = memberService.existsNickName(nickname);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }
}
