package com.t2m.g2nee.front.couponset.coupon.controller;

import com.t2m.g2nee.front.couponset.coupon.service.CouponService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage/coupons")
public class CouponMyPageController {

    private final CouponService couponService;

    public CouponMyPageController(CouponService couponService) {
        this.couponService = couponService;

    }

    //쿠폰 전체 조회
    
    //책에 대해 사용할 수 있는 쿠폰 조회: couponService.getBookCoupons(customerId, bookId, page);

    
    //전체 주문에 대해 사용할 수 있는 쿠폰 조회: couponService.getTotalCoupons(customerId, page);

}
