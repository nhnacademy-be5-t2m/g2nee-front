package com.t2m.g2nee.front.couponset.coupon.controller;

import static com.t2m.g2nee.front.aop.MemberAspect.MEMBER_INFO;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.couponset.coupon.service.CouponService;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mypage/coupons")
public class CouponMyPageController {

    private final CouponService couponService;
    private final MemberAspect memberAspect;

    public CouponMyPageController(CouponService couponService, MemberAspect memberAspect) {
        this.couponService = couponService;
        this.memberAspect = memberAspect;
    }

    /**
     * 자신이 가진 모든 쿠폰을 리턴
     * @param page 현재 페이지
     * @param model 모델
     * @return 내 쿠폰 목록
     */
    @Member
    @GetMapping
    public String getMyCoupon(@RequestParam(defaultValue = "1") int page, Model model) {
        MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
        Long memberId = null;
        if (member != null) {
            memberId = member.getMemberId();
        }

        model.addAttribute("coupons", couponService.getMyCoupons(memberId, page));
        return "mypage/couponPage";
    }

    //책에 대해 사용할 수 있는 쿠폰 조회: couponService.getBookCoupons(customerId, bookId, page);


    //전체 주문에 대해 사용할 수 있는 쿠폰 조회: couponService.getTotalCoupons(customerId, page);

}
