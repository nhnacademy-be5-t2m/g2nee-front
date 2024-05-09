package com.t2m.g2nee.front.bookset.contributor.service;

import com.t2m.g2nee.front.bookset.contributor.adaptor.ContributorAdaptor;
import com.t2m.g2nee.front.bookset.contributor.dto.ContributorDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 기여자 service 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Service
@Transactional
public class ContributorService {

    private final ContributorAdaptor contributorAdaptor;

    public ContributorService(ContributorAdaptor contributorAdaptor) {
        this.contributorAdaptor = contributorAdaptor;
    }

    public List<ContributorDto.Response> getAllContributor() {

        return contributorAdaptor.getAllContributor();
    }

    /**
     * 기여자 등록 메서드
     *
     * @param request 등록할 정보가 있는 객체
     */
    public void registerContributor(ContributorDto.Request request) {

        contributorAdaptor.registerContributor(request);

    }

    /**
     * 기여자 조회 메서드
     *
     * @param page 기여자 번호
     *             PageResponse<ContributorDto.Response>
     */
    public PageResponse<ContributorDto.Response> getAllContributor(int page) {

        return contributorAdaptor.getAllContributor(page);
    }

    /**
     * 기여자 수정 메서드
     *
     * @param contributorId 태그 아이디
     * @param request       수정할 정보가 담긴 객체
     */
    public void updateContributor(Long contributorId, ContributorDto.Request request) {

        contributorAdaptor.updateContributor(contributorId, request);

    }

    /**
     * 역할 삭제 메서드
     *
     * @param contributorId 태그 아이디
     */
    public void deleteContributor(Long contributorId) {

        contributorAdaptor.deleteContributor(contributorId);
    }
}
