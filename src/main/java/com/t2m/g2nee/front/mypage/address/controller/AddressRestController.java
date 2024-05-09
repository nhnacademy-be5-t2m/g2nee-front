package com.t2m.g2nee.front.mypage.address.controller;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.mypage.address.dto.request.AddressRequestDto;
import com.t2m.g2nee.front.mypage.address.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressRestController {
    private final MyPageService myPageService;
    private final MemberAspect memberAspect;


    /**
     * 기본 배송지를 바꾸는 메소드
     *
     * @param addressId 기본 배송지로 바꿀 배송지 id
     * @return shop으로 수정요청보낸 것에 대한 응답
     */
    @PostMapping("/changeDefaultAddress")
    public ResponseEntity<Void> changeDefaultAddress(@RequestBody String addressId) {
        return myPageService.changeDefaultAddress(Long.valueOf(addressId));
    }

    /**
     * 배송지를 추가하는 메소드
     *
     * @param addressRequestDto 추가하려는 배송지 정보
     * @return shop으로 주소 등록 요청보낸 것에 대한 응답
     */
    @PostMapping("/save")
    @Member
    public ResponseEntity<Void> saveAddress(@RequestBody AddressRequestDto addressRequestDto) {
        Long memberId = (Long) memberAspect.getThreadLocal().get();
        addressRequestDto.setMemberId(memberId);
        myPageService.saveAddress(addressRequestDto);
        return ResponseEntity.ok().build();
    }


}
