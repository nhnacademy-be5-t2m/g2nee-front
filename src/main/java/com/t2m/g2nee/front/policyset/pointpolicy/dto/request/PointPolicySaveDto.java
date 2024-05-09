package com.t2m.g2nee.front.policyset.pointpolicy.dto.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 포인트 정책 저장을 위한 객체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PointPolicySaveDto {

    /**
     * 포인트 정책 이름
     */
    private String policyName;

    /**
     * 포인트 정책 종류
     */
    private String policyType;

    /**
     * 적립 금액
     */
    private BigDecimal amount;
}