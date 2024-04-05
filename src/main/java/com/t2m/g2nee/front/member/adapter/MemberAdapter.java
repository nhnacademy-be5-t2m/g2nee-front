package com.t2m.g2nee.front.member.adapter;

import com.t2m.g2nee.front.config.HeaderConfiguration;
import com.t2m.g2nee.front.member.dto.SignupRequestDto;
import com.t2m.g2nee.front.member.dto.SignupResponseDto;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "member", url = "${g2nee.gateway}", configuration = HeaderConfiguration.class)
public interface MemberAdapter {

    @PostMapping("/shop/member/signup")
    Optional<SignupResponseDto> signup(@RequestBody SignupRequestDto request);

}
