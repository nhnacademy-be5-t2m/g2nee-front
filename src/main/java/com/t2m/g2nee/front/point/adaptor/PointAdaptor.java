package com.t2m.g2nee.front.point.adaptor;

import com.t2m.g2nee.front.point.dto.PointResponseDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;

/**
 * 포인트 adaptor 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
public interface PointAdaptor {

    PageResponse<PointResponseDto> getMemberPointDetail(Long memberId);
}
