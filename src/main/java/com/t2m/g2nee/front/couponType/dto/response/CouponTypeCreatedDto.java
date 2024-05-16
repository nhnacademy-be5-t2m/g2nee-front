package com.t2m.g2nee.front.couponType.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CouponTypeCreatedDto {
    private Integer period;
    private String name;
    private String type;
    private BigDecimal discount;
    private BigDecimal  minimumOrderAmount;
    private BigDecimal maximumDiscount;
    private String  status;
}
