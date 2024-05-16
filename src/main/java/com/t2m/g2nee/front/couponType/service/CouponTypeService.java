package com.t2m.g2nee.front.couponType.service;


import com.t2m.g2nee.front.couponType.dto.request.CouponTypeRequestDto;
import com.t2m.g2nee.front.couponType.dto.response.CouponTypeInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;

/**
 * 쿠폰 타입 처리에 관한 서비스입니다.
 *
 * @author : 김수현
 * @since : 1.0
 */
public interface CouponTypeService {

    /**
     * 쿠폰타입을 생성하는 메소드입니다.
     *
     * @param couponTypeRequestDto
     */
    void createCouponType(CouponTypeRequestDto couponTypeRequestDto);

    /**
     * 북쿠폰타입을 생성하는 메소드입니다.
     *
     * @param couponTypeRequestDto
     */

    void createBookCoupon(CouponTypeRequestDto couponTypeRequestDto);

    /**
     * 카테고리 쿠폰타입을 생성하는 메소드입니다.
     *
     * @param couponTypeRequestDto
     */
    void createCategoryCoupon(CouponTypeRequestDto couponTypeRequestDto);

    /**
     * 모든 쿠폰타입을 페이징하여 가져오는 메소드입니다.
     *
     * @param page
     * @return
     */
    PageResponse<CouponTypeInfoDto> getAllCouponTypes(int page);
}
