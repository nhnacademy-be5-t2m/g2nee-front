package com.t2m.g2nee.front.bookset.contributor.adaptor;

import com.t2m.g2nee.front.bookset.contributor.dto.ContributorDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;


/**
 * 기여자 adaptor interface
 *
 * @author : 신동민
 * @since : 1.0
 */
public interface ContributorAdaptor {

    /**
     * 모든 기여자를 조회하는 메서드
     *
     * @return List<ContributorDto.Response>
     */
    List<ContributorDto.Response> getAllContributor();

    /**
     * 기여자 등록 메서드
     *
     * @param request 등록할 정보가 있는 객체
     */
    void registerContributor(ContributorDto.Request request);

    /**
     * 기여자 조회 메서드
     *
     * @param page 기여자 번호
     *             PageResponse<ContributorDto.Response>
     */
    PageResponse<ContributorDto.Response> getAllContributor(int page);

    /**
     * 기여자 수정 메서드
     *
     * @param contributorId 태그 아이디
     * @param request       수정할 정보가 담긴 객체
     */
    void updateContributor(Long contributorId, ContributorDto.Request request);

    /**
     * 역할 삭제 메서드
     *
     * @param contributorId 태그 아이디
     */
    void deleteContributor(Long contributorId);
}
