package com.t2m.g2nee.front.policyset.deliveryPolicy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 배송비 정책 저장을 위한 dto입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeliveryPolicySaveDto {

    /**
     * 배송비
     */
    private int deliveryFee;

    /**
     * 무료 배송 기준
     */
    private int freeDeliveryStandard;
}
