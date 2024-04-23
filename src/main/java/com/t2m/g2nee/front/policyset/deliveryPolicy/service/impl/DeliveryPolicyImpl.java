package com.t2m.g2nee.front.policyset.deliveryPolicy.service.impl;

import com.t2m.g2nee.front.exception.CustomException;
import com.t2m.g2nee.front.policyset.deliveryPolicy.adaptor.DeliveryPolicyAdaptor;
import com.t2m.g2nee.front.policyset.deliveryPolicy.dto.request.DeliveryPolicySaveDto;
import com.t2m.g2nee.front.policyset.deliveryPolicy.dto.response.DeliveryPolicyInfoDto;
import com.t2m.g2nee.front.policyset.deliveryPolicy.service.DeliveryPolicyService;
import com.t2m.g2nee.front.utils.PageResponse;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPolicyImpl implements DeliveryPolicyService {

    private final DeliveryPolicyAdaptor adaptor;

    public DeliveryPolicyImpl(DeliveryPolicyAdaptor adaptor) {
        this.adaptor = adaptor;
    }

    @Override
    public void createDeliveryPolicy(DeliveryPolicySaveDto request) {
        adaptor.requestCreateDeliveryPolicy(request);
    }

    @Override
    public DeliveryPolicyInfoDto getDeliveryPolicy() {
        try{
            return adaptor.getDeliveryPolicy();
        }catch(CustomException e){
            return null;
        }
    }

    @Override
    public PageResponse<DeliveryPolicyInfoDto> getAllDeliveryPolicies(int page) {
        return adaptor.getAllDeliveryPolicies(page);
    }
}
