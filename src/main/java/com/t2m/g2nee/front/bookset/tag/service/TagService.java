package com.t2m.g2nee.front.bookset.tag.service;

import com.t2m.g2nee.front.bookset.tag.dto.TagDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TagService {

    private final RestTemplate restTemplate;
    @Value("${g2nee.gateway}")
    String gatewayUrl;

    public List<TagDto.Response> getAllTag(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/shop/tags/list";

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<TagDto.Response>>() {
                }
        ).getBody();
    }

    /**
     * 태그 등록 메서드
     * @param request 등록할 정보가 있는 객체
     */
    public void registerTag(TagDto.Request request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TagDto.Request> requestEntity = new HttpEntity<>(request, headers);

        String url = gatewayUrl + "/shop/tags";

        restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

    }

    /**
     * 태그 조회 메서드
     *
     * @param page 페이지 번호
     * @return PageResponse<TagDto.Response>
     */
    public PageResponse<TagDto.Response> getAllTag(int page) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/shop/tags?page=" + page;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<PageResponse<TagDto.Response>>() {
                }
        ).getBody();
    }

    /**
     * 역할 수정 메서드
     * @param tagId 태그 아이디
     * @param request 수정할 정보가 담긴 객체
     */
    public void updateTag(Long tagId, TagDto.Request request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TagDto.Request> requestEntity = new HttpEntity<>(request, headers);

        String url = gatewayUrl + "/shop/tags/" + tagId;

        restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                requestEntity,
                String.class
        );

    }

    /**
     * 역할 삭제 메서드
     * @param tagId 태그 아이디
     */
    public void deleteTag(Long tagId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/shop/tags/" + tagId;

        restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );

    }
}
