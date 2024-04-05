package com.t2m.g2nee.front.member.service;


import com.t2m.g2nee.front.member.adapter.MemberAdapter;
import com.t2m.g2nee.front.member.dto.SignupRequestDto;
import com.t2m.g2nee.front.member.dto.SignupResponseDto;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 회원가입에 필요한 service
 *
 * @author : 정지은
 * @since : 1.0
 */
@Service
public class MemberService {

    @Autowired
    private MemberAdapter memberAdapter;

    public SignupResponseDto signup(SignupRequestDto request) {
        Optional<SignupResponseDto> signupResponseDtoOptional = memberAdapter.signup(request);
        if (signupResponseDtoOptional.isEmpty()) {
            throw new RuntimeException("회원가입에 실패하였습니다.");
        }
        return signupResponseDtoOptional.get();
    }
}