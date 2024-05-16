package com.t2m.g2nee.front.couponType.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 쿠폰 생성을 위한 dto입니다.
 * @author : 김수현
 * @since : 1.0
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CouponTypeRequestDto {

    private Long couponTypeId;
    private String name;
    private Integer period;
    private String type;
    private BigDecimal discount;
    private BigDecimal minimumOrderAmount;
    private BigDecimal maximumDiscount;
    private String status;
    private Long bookId;
    private Long categoryId;


    public CouponTypeRequestDto(Long couponTypeId, String name, Integer period, String type, BigDecimal discount, BigDecimal minimumOrderAmount, BigDecimal maximumDiscount, String status) {
        this.couponTypeId = couponTypeId;
        this.name = name;
        this.period = period;
        this.type = type;
        this.discount = discount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.maximumDiscount = maximumDiscount;
        this.status = status;
    }


    public CouponTypeRequestDto(Long couponTypeId, String name, Integer period, String type, BigDecimal discount, BigDecimal minimumOrderAmount, BigDecimal maximumOrderAmount, String status, Long bookId) {
        this.couponTypeId = couponTypeId;
        this.name = name;
        this.period = period;
        this.type = type;
        this.discount = discount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.maximumDiscount = maximumDiscount;
        this.status = status;
        this.bookId= bookId;
    }
}
