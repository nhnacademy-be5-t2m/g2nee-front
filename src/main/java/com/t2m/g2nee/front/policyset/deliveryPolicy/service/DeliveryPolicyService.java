package com.t2m.g2nee.front.policyset.deliveryPolicy.service;

import com.t2m.g2nee.front.policyset.deliveryPolicy.dto.request.DeliveryPolicySaveDto;
import com.t2m.g2nee.front.policyset.deliveryPolicy.dto.response.DeliveryPolicyInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;

public interface DeliveryPolicyService {

    /**
     * 배송비 정책을 저장합니다.
     * @param request
     * @return
     */
    void createDeliveryPolicy(DeliveryPolicySaveDto request);

    /**
     * 제일 최근의 배송비 정책을 얻습니다.
     * @return
     */
    DeliveryPolicyInfoDto getDeliveryPolicy();

    /**
     * 모든 배송비 정책을 페이징하여 가져옵니다.
     * @param page
     * @return
     */
    PageResponse<DeliveryPolicyInfoDto> getAllDeliveryPolicies(int page);
}
