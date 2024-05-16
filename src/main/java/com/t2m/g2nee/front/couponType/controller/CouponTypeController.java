package com.t2m.g2nee.front.couponType.controller;

import com.t2m.g2nee.front.couponType.dto.request.CouponTypeRequestDto;
import com.t2m.g2nee.front.couponType.service.CouponTypeService;
import com.t2m.g2nee.front.couponset.coupon.dto.request.CouponIssueDto;
import com.t2m.g2nee.front.couponset.coupon.service.CouponService;
import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 관리자가 쿠폰 타입을 관리하기 위한 controller입니다.
 *
 * @author : 김수현
 * @since : 1.0
 */
@Controller
@RequestMapping("/admin/couponType")
public class CouponTypeController {

    private final CouponTypeService couponTypeService;

    private final CouponService couponService;

    public CouponTypeController(CouponTypeService couponTypeService, CouponService couponService) {
        this.couponTypeService = couponTypeService;
        this.couponService = couponService;
    }

    /**
     * CouponType을 페이징처리해 보여줍니다.
     *
     * @param page
     * @param model
     * @return
     */
    @GetMapping
    public String CouponTypePage(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("CouponTypes", couponTypeService.getAllCouponTypes(page));
        return "admin/couponType/adminCouponTypeList";
    }

    /**
     * 쿠폰타입 저장 양식을 보여줍니다.
     *
     * @return
     */
    @GetMapping("/save")
    public String CouponTypeCreateForm() {
        return "admin/couponType/adminCouponTypeCreateFrom";
    }

    /**
     * 실제 쿠폰타입을 저장합니다.
     *
     * @param couponTypeId
     * @param name
     * @param period
     * @param type
     * @param discount
     * @param minimumOrderAmount
     * @param maximumOrderAmount
     * @param status
     * @return
     */
    @PostMapping("/save")
    public String createCouponTypes(@RequestParam("couponTypeId") Long couponTypeId,
                                    @RequestParam("name") String name,
                                    @RequestParam("period") Integer period,
                                    @RequestParam("type") String type,
                                    @RequestParam("discount") BigDecimal discount,
                                    @RequestParam("minimumOrderAmount") BigDecimal minimumOrderAmount,
                                    @RequestParam("maximumOrderAmount") BigDecimal maximumOrderAmount,
                                    @RequestParam("status") String status
    ) {
        CouponTypeRequestDto requestDto =
                new CouponTypeRequestDto(couponTypeId, name, period, type, discount, minimumOrderAmount,
                        maximumOrderAmount, status);
        couponTypeService.createCouponType(requestDto);
        return "redirect:/admin/couponType";
    }

    /**
     * 실제 북 쿠폰타입을 저장합니다.
     *
     * @param couponTypeId
     * @param name
     * @param period
     * @param type
     * @param discount
     * @param minimumOrderAmount
     * @param maximumOrderAmount
     * @param status
     * @return
     */
    @PostMapping("/save/bookCoupon")
    public String createBookCoupon(@RequestParam("couponTypeId") Long couponTypeId,
                                   @RequestParam("name") String name,
                                   @RequestParam("period") Integer period,
                                   @RequestParam("type") String type,
                                   @RequestParam("discount") BigDecimal discount,
                                   @RequestParam("minimumOrderAmount") BigDecimal minimumOrderAmount,
                                   @RequestParam("maximumOrderAmount") BigDecimal maximumOrderAmount,
                                   @RequestParam("status") String status,
                                   @RequestParam("bookId") Long bookId
    ) {
        CouponTypeRequestDto requestDto =
                new CouponTypeRequestDto(couponTypeId, name, period, type, discount, minimumOrderAmount,
                        maximumOrderAmount, status, bookId);
        couponTypeService.createBookCoupon(requestDto);
        return "redirect:/admin/couponType";
    }

    @PostMapping("/save/categoryCoupon")
    public String createCategoryCoupon(@RequestParam("couponTypeId") Long couponTypeId,
                                       @RequestParam("name") String name,
                                       @RequestParam("period") Integer period,
                                       @RequestParam("type") String type,
                                       @RequestParam("discount") BigDecimal discount,
                                       @RequestParam("minimumOrderAmount") BigDecimal minimumOrderAmount,
                                       @RequestParam("maximumOrderAmount") BigDecimal maximumOrderAmount,
                                       @RequestParam("status") String status,
                                       @RequestParam("categoryId") Long categoryId
    ) {
        CouponTypeRequestDto requestDto =
                new CouponTypeRequestDto(couponTypeId, name, period, type, discount, minimumOrderAmount,
                        maximumOrderAmount, status, categoryId);
        couponTypeService.createCategoryCoupon(requestDto);
        return "redirect:/admin/couponType";
    }


    @PostMapping("/issue")
    public String issueCoupon(@RequestParam Long couponTypeId) {
        couponService.issueCoupon(new CouponIssueDto(couponTypeId));
        return "redirect:/admin/couponType";
    }

}
