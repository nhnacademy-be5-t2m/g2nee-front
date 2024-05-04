package com.t2m.g2nee.front.policyset.pointPolicy.adaptor;


import com.t2m.g2nee.front.policyset.pointPolicy.dto.request.PointPolicySaveDto;
import com.t2m.g2nee.front.policyset.pointPolicy.dto.response.PointPolicyInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;

/**
 * shop을 통해 포인트 정책에 관련된 정보를 받는 어댑터 인터페이스입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface PointPolicyAdaptor {

    /**
     * 포인트 정책을 저장합니다.
     *
     * @param request
     * @return
     */
    PointPolicyInfoDto requestCreatePointPolicy(PointPolicySaveDto request);

    /**
     * 포인트 정책을 수정합니다.
     *
     * @param pointPolicyId
     * @param request
     * @return
     */
    PointPolicyInfoDto requestUpdatePointPolicy(Long pointPolicyId, PointPolicySaveDto request);

    /**
     * 포인트 정책을 soft delete합니다.
     *
     * @param pointPolicyId
     * @return
     */
    boolean requestDeletePointPolicy(Long pointPolicyId);

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

    PointPolicyInfoDto getPointPolicyByPolicyName(String policyName);
}
