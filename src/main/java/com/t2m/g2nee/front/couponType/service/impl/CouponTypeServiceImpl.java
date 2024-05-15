package com.t2m.g2nee.front.couponType.service.impl;

import com.t2m.g2nee.front.couponType.adaptor.CouponTypeAdaptor;
import com.t2m.g2nee.front.couponType.dto.request.CouponTypeRequestDto;
import com.t2m.g2nee.front.couponType.dto.response.CouponTypeInfoDto;
import com.t2m.g2nee.front.couponType.service.CouponTypeService;
import com.t2m.g2nee.front.utils.PageResponse;
import org.springframework.stereotype.Service;

@Service
public class CouponTypeServiceImpl implements CouponTypeService {

    private final CouponTypeAdaptor adaptor;

    public CouponTypeServiceImpl(CouponTypeAdaptor adaptor) {
        this.adaptor = adaptor;
    }


    @Override
    public void createCouponType(CouponTypeRequestDto couponTypeRequestDto) {
        adaptor.requestCreateCouponType(couponTypeRequestDto);


    }

    @Override
    public void createBookCoupon(CouponTypeRequestDto couponTypeRequestDto) {
        adaptor.requestCreateBookCoupon(couponTypeRequestDto);
    }

    @Override
    public void createCategoryCoupon(CouponTypeRequestDto couponTypeRequestDto) {
        adaptor.requestCreateCategoryCoupon(couponTypeRequestDto);

    }

    @Override
    public PageResponse<CouponTypeInfoDto> getAllCouponTypes(int page) {
        return adaptor.getAllCouponTypes(page);
    }
}
