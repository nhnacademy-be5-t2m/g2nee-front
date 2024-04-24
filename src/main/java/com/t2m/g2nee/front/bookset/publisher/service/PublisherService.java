package com.t2m.g2nee.front.bookset.publisher.service;

import com.t2m.g2nee.front.bookset.publisher.adaptor.PublisherAdaptor;
import com.t2m.g2nee.front.bookset.publisher.dto.PublisherDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 출판사 관리 service 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Service
@Transactional
public class PublisherService {

    private final PublisherAdaptor publisherAdaptor;

    public PublisherService(PublisherAdaptor publisherAdaptor) {
        this.publisherAdaptor = publisherAdaptor;
    }

    /**
     * 출판사 등록 메서드
     *
     * @param request 등록할 정보가 있는 객체
     */
    public void registerPublisher(PublisherDto.Request request) {

        publisherAdaptor.registerPublisher(request);
    }

    /**
     * 출판사 조회 메서드
     *
     * @return List<PublisherDto.Response>
     */
    public List<PublisherDto.Response> getAllPublisher() {

        return publisherAdaptor.getAllPublisher();
    }

    /**
     * 출판사 조회 메서드
     *
     * @param page 페이지 번호
     * @return PageResponse<PublisherDto.Response>
     */
    public PageResponse<PublisherDto.Response> getAllPublisher(int page) {

        return publisherAdaptor.getAllPublisher(page);
    }

    /**
     * 출판사 수정 메서드
     *
     * @param publisherId 출판사 아이디
     * @param request     수정할 정보가 담긴 객체
     */
    public void updatePublisher(Long publisherId, PublisherDto.Request request) {

        publisherAdaptor.updatePublisher(publisherId, request);

    }

    public void deletePublisher(Long publisherId) {
        publisherAdaptor.deletePublisher(publisherId);

    }
}
