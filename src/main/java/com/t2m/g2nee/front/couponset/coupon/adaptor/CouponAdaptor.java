package com.t2m.g2nee.front.couponset.coupon.adaptor;

import com.t2m.g2nee.front.couponset.coupon.dto.request.CouponDownloadDto;
import com.t2m.g2nee.front.couponset.coupon.dto.request.CouponIssueDto;
import com.t2m.g2nee.front.couponset.coupon.dto.response.CouponInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;

public interface CouponAdaptor {

    CouponInfoDto requestIssueCoupon(CouponIssueDto request);

    CouponInfoDto requestDownloadCoupon(CouponDownloadDto request);

    PageResponse<CouponInfoDto> getMyCoupons(Long customerId, int page);

    PageResponse<CouponInfoDto> getBookCoupons(Long customerId, Long bookId, int page);

    PageResponse<CouponInfoDto> getTotalCoupons(Long customerId, int page);
}
