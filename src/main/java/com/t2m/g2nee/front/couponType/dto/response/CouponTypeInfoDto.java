package com.t2m.g2nee.front.couponType.dto.response;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * admin페이지에서 모든 쿠폰타입을 보여주기 위한 dto입니다.
 */


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CouponTypeInfoDto {

    private Long couponTypeId;

    private String couponTypeName;

    private int period;

    private String type;

    private BigDecimal discount;

    private BigDecimal  minimumOrderAmount;

    private BigDecimal maximumDiscount;

    private Long categoryId;

    private Long bookId;

    private String  status;
}
