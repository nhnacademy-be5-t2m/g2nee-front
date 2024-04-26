package com.t2m.g2nee.front.policyset.pointPolicy.service.impl;

import com.t2m.g2nee.front.policyset.pointPolicy.adaptor.PointPolicyAdaptor;
import com.t2m.g2nee.front.policyset.pointPolicy.dto.request.PointPolicySaveDto;
import com.t2m.g2nee.front.policyset.pointPolicy.dto.response.PointPolicyInfoDto;
import com.t2m.g2nee.front.policyset.pointPolicy.service.PointPolicyService;
import com.t2m.g2nee.front.utils.PageResponse;
import org.springframework.stereotype.Service;

/**
 * PointPolicyService의 구현체 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Service
public class PointPolicyServiceImpl implements PointPolicyService {

    private final PointPolicyAdaptor adaptor;

    public PointPolicyServiceImpl(PointPolicyAdaptor adaptor) {
        this.adaptor = adaptor;
    }

    @Override
    public void createPointPolicy(PointPolicySaveDto request) {
        adaptor.requestCreatePointPolicy(request);
    }

    @Override
    public void updatePointPolicy(Long pointPolicyId, PointPolicySaveDto request) {
        adaptor.requestUpdatePointPolicy(pointPolicyId, request);
    }

    @Override
    public void deletePointPolicy(Long pointPolicyId) {
        adaptor.requestDeletePointPolicy(pointPolicyId);
    }

    @Override
    public PointPolicyInfoDto getPointPolicy(Long pointPolicyId) {
        return adaptor.getPointPolicy(pointPolicyId);
    }

    @Override
    public PageResponse<PointPolicyInfoDto> getAllPointPolicies(int page) {
        return adaptor.getAllPointPolicies(page);
    }
}
