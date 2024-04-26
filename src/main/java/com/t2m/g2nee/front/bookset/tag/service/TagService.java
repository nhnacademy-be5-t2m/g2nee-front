package com.t2m.g2nee.front.bookset.tag.service;

import com.t2m.g2nee.front.bookset.tag.adaptor.TagAdaptor;
import com.t2m.g2nee.front.bookset.tag.dto.TagDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 태그 service 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Service
@Transactional
public class TagService {

    private final TagAdaptor tagAdaptor;

    public TagService(TagAdaptor tagAdaptor) {
        this.tagAdaptor = tagAdaptor;
    }

    public List<TagDto.Response> getAllTag() {

        return tagAdaptor.getAllTag();
    }

    /**
     * 태그 등록 메서드
     *
     * @param request 등록할 정보가 있는 객체
     */
    public void registerTag(TagDto.Request request) {

        tagAdaptor.registerTag(request);

    }

    /**
     * 태그 조회 메서드
     *
     * @param page 페이지 번호
     * @return PageResponse<TagDto.Response>
     */
    public PageResponse<TagDto.Response> getAllTag(int page) {
        return tagAdaptor.getAllTag(page);
    }

    /**
     * 역할 수정 메서드
     *
     * @param tagId   태그 아이디
     * @param request 수정할 정보가 담긴 객체
     */
    public void updateTag(Long tagId, TagDto.Request request) {

        tagAdaptor.updateTag(tagId, request);

    }

    /**
     * 역할 삭제 메서드
     *
     * @param tagId 태그 아이디
     */
    public void deleteTag(Long tagId) {

        tagAdaptor.deleteTag(tagId);

    }
}
