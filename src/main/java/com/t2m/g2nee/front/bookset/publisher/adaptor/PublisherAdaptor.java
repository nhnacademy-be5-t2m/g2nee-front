package com.t2m.g2nee.front.bookset.publisher.adaptor;

import com.t2m.g2nee.front.bookset.publisher.dto.PublisherDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;


/**
 * 출판사 adaptor interface
 *
 * @author : 신동민
 * @since : 1.0
 */
public interface PublisherAdaptor {

    /**
     * 출판사 등록 메서드
     *
     * @param request 등록할 정보가 있는 객체
     */
     void registerPublisher(PublisherDto.Request request);

    /**
     * 출판사 조회 메서드
     *
     * @return List<PublisherDto.Response>
     */
     List<PublisherDto.Response> getAllPublisher();

    /**
     * 출판사 조회 메서드
     *
     * @param page 페이지 번호
     * @return PageResponse<PublisherDto.Response>
     */
     PageResponse<PublisherDto.Response> getAllPublisher(int page);

    /**
     * 출판사 수정 메서드
     *
     * @param publisherId 출판사 아이디
     * @param request     수정할 정보가 담긴 객체
     */
     void updatePublisher(Long publisherId, PublisherDto.Request request);

    /**
     * 출판사 삭제 메서드
     * @param publisherId 출판사 아이디
     */
     void deletePublisher(Long publisherId);
}
