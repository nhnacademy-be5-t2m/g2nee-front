package com.t2m.g2nee.front.mypage.address.controller;

import static com.t2m.g2nee.front.aop.MemberAspect.MEMBER_INFO;
import static com.t2m.g2nee.front.aop.MemberAspect.MEMBER_INFO_KEY;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.mypage.address.dto.request.AddressRequestDto;
import com.t2m.g2nee.front.mypage.address.dto.response.AddressResponseDto;
import com.t2m.g2nee.front.mypage.address.service.MyPageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage/address")
@RequiredArgsConstructor
public class AddressController {
    private final MyPageService mypageService;
    private final MemberAspect memberAspect;

    /**
     * 전체 주소 list 페이지를 띄워주는 메소드
     *
     * @param model
     * @return 전체 주소 list를 보여주는 페이지
     */
    @Member
    @GetMapping
    public String myPageAddress(Model model) {
        MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
        Long memberId = null;
        if(member!=null){
            memberId = member.getMemberId();
        }
        List<AddressResponseDto> addressList = mypageService.getAddressListByMemberId(memberId);
        model.addAttribute("addressList", addressList);
        return "mypage/addressPage";
    }

    /**
     * 주소를 저장할 페이지를 보여주는 메소드
     *
     * @param model
     * @return 주소 저장을 위한 form입력 페이지
     */
    @GetMapping("/save")
    public String saveAddress(Model model) {
        model.addAttribute("addressForm", new AddressRequestDto());
        return "mypage/saveAddressPage";
    }

    /**
     * 주소를 삭제하고 다시 주소 페이지를 보여주는 메소드
     *
     * @param addressId 삭제할 주소의 id
     * @return 다시 주소페이지로 redirect
     */
    @GetMapping("/delete/{addressId}")
    public String deleteAddress(@PathVariable Long addressId) {
        mypageService.deleteAddress(addressId);
        return "redirect:/mypage/address";
    }

}
