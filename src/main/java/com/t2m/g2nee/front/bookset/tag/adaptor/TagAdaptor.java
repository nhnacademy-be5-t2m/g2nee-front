package com.t2m.g2nee.front.bookset.tag.adaptor;

import com.t2m.g2nee.front.bookset.tag.dto.TagDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;

/**
 * 태그 adaptor interface
 *
 * @author : 신동민
 * @since : 1.0
 */
public interface TagAdaptor {
    /**
     * 모든 태그를 조회하는 메서드
     */
    List<TagDto.Response> getAllTag();

    /**
     * 태그 등록 메서드
     *
     * @param request 등록할 정보가 있는 객체
     */
    void registerTag(TagDto.Request request);

    /**
     * 태그 조회 메서드
     *
     * @param page 페이지 번호
     * @return PageResponse<TagDto.Response>
     */
    PageResponse<TagDto.Response> getAllTag(int page);

    /**
     * 역할 수정 메서드
     *
     * @param tagId   태그 아이디
     * @param request 수정할 정보가 담긴 객체
     */
    void updateTag(Long tagId, TagDto.Request request);

    /**
     * 역할 삭제 메서드
     *
     * @param tagId 태그 아이디
     */
    void deleteTag(Long tagId);
}
