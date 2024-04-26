package com.t2m.g2nee.front.mypage.controller;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.mypage.dto.response.AddressResponseDto;
import com.t2m.g2nee.front.mypage.service.MyPageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final MyPageService mypageService;
    private final MemberAspect memberAspect;

    @Member
    @GetMapping("/address")
    public String myPageAddress(Model model) {
        Long memberId = (Long) memberAspect.getThreadLocal().get();
        List<AddressResponseDto> addressList = mypageService.getAddressListByMemberId(memberId);
        model.addAttribute("addressList", addressList);
        return "mypage/address";
    }
}
