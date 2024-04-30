package com.t2m.g2nee.front.policyset.deliveryPolicy.adaptor;

import com.t2m.g2nee.front.policyset.deliveryPolicy.dto.request.DeliveryPolicySaveDto;
import com.t2m.g2nee.front.policyset.deliveryPolicy.dto.response.DeliveryPolicyInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;

/**
 * shop에서 배송비 정책과 관련된 값들을 가져오기 위한 어댑터의 인터페이스입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface DeliveryPolicyAdaptor {

    /**
     * 배송비 정책을 저장합니다.
     *
     * @param request
     * @return
     */
    DeliveryPolicyInfoDto requestCreateDeliveryPolicy(DeliveryPolicySaveDto request);

    /**
     * 제일 최근의 배송비 정책을 얻습니다.
     *
     * @return
     */
    DeliveryPolicyInfoDto getDeliveryPolicy();

    /**
     * 모든 배송비 정책을 페이징하여 가져옵니다.
     *
     * @param page
     * @return
     */
    PageResponse<DeliveryPolicyInfoDto> getAllDeliveryPolicies(int page);
}
