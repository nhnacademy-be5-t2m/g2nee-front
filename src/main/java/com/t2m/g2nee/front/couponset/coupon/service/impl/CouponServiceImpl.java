package com.t2m.g2nee.front.couponset.coupon.service.impl;

import com.t2m.g2nee.front.couponset.coupon.adaptor.CouponAdaptor;
import com.t2m.g2nee.front.couponset.coupon.dto.request.CouponDownloadDto;
import com.t2m.g2nee.front.couponset.coupon.dto.request.CouponIssueDto;
import com.t2m.g2nee.front.couponset.coupon.dto.response.CouponInfoDto;
import com.t2m.g2nee.front.couponset.coupon.service.CouponService;
import com.t2m.g2nee.front.utils.PageResponse;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponAdaptor couponAdaptor;

    public CouponServiceImpl(CouponAdaptor couponAdaptor) {
        this.couponAdaptor = couponAdaptor;
    }

    @Override
    public CouponInfoDto issueCoupon(CouponIssueDto request) {
        return couponAdaptor.requestIssueCoupon(request);
    }

    @Override
    public CouponInfoDto downloadCoupon(CouponDownloadDto request) {
        return couponAdaptor.requestDownloadCoupon(request);
    }

    @Override
    public PageResponse<CouponInfoDto> getMyCoupons(Long customerId, int page) {
        return couponAdaptor.getMyCoupons(customerId, page);
    }

    @Override
    public PageResponse<CouponInfoDto> getBookCoupons(Long customerId, Long bookId, int page) {
        return couponAdaptor.getBookCoupons(customerId, bookId, page);
    }

    @Override
    public PageResponse<CouponInfoDto> getTotalCoupons(Long customerId, int page) {
        return couponAdaptor.getTotalCoupons(customerId, page);
    }
}
