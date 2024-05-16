package com.t2m.g2nee.front.couponType.adaptor;

import com.t2m.g2nee.front.couponType.dto.request.CouponTypeRequestDto;
import com.t2m.g2nee.front.couponType.dto.response.CouponTypeCreatedDto;
import com.t2m.g2nee.front.couponType.dto.response.CouponTypeInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;

/**
 * shop에서 쿠폰타입과 관련된 값들을 가져오기 위한 어댑터의 인터페이스
 *
 * @author : 김수현
 * @since : 1.0
 */
public interface CouponTypeAdaptor {

    /**
     * 쿠폰을 저장합니다.
     *
     * @param couponTypeRequestDto
     * @return
     */
    CouponTypeCreatedDto requestCreateCouponType(CouponTypeRequestDto couponTypeRequestDto);

    CouponTypeCreatedDto requestCreateBookCoupon(CouponTypeRequestDto couponTypeRequestDto);

    CouponTypeCreatedDto requestCreateCategoryCoupon(CouponTypeRequestDto couponTypeRequestDto);

    PageResponse<CouponTypeInfoDto> getAllCouponTypes(int page);


}
