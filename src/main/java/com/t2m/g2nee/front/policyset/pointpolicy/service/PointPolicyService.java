package com.t2m.g2nee.front.policyset.pointpolicy.service;

import com.t2m.g2nee.front.policyset.pointpolicy.dto.request.PointPolicySaveDto;
import com.t2m.g2nee.front.policyset.pointpolicy.dto.response.PointPolicyInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;

/**
 * 포인트 정책 서비스입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface PointPolicyService {

    /**
     * 포인트 정책을 저장합니다.
     *
     * @param request
     * @return
     */
    void createPointPolicy(PointPolicySaveDto request);

    /**
     * 포인트 정책을 수정합니다.
     *
     * @param pointPolicyId
     * @param request
     * @return
     */
    void updatePointPolicy(Long pointPolicyId, PointPolicySaveDto request);

    /**
     * 포인트 정책을 soft delete합니다.
     *
     * @param pointPolicyId
     * @return
     */
    void deletePointPolicy(Long pointPolicyId);

    /**
     * 특정 포인트 정책을 가져옵니다.
     *
     * @param pointPolicyId
     * @return
     */
    PointPolicyInfoDto getPointPolicy(Long pointPolicyId);

    /**
     * 모든 포인트 정책을 페이징처리하여 반환합니다.
     *
     * @param page
     * @return
     */
    PageResponse<PointPolicyInfoDto> getAllPointPolicies(int page);

    /**
     * 포인트 이름으로 특정 포인트 정책을 가져옵니다.
     *
     * @param policyName
     * @return
     */
    PointPolicyInfoDto getPointPolicyByPolicyName(String policyName);
}
