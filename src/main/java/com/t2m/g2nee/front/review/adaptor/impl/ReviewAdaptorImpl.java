package com.t2m.g2nee.front.review.adaptor.impl;

import com.t2m.g2nee.front.review.adaptor.ReviewAdaptor;
import com.t2m.g2nee.front.review.dto.ReviewDto;
import com.t2m.g2nee.front.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class ReviewAdaptorImpl implements ReviewAdaptor {

    private final RestTemplate restTemplate;
    @Value("${g2nee.gateway}")
    private String gatewayUrl;

    /**
     * 리뷰 작성 메서드
     *
     * @param image   이미지
     * @param request 리뷰 정보 객체
     */
    @Override
    public void postReview(MultipartFile image, ReviewDto.Request request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("request", request);
        if (image != null) {
            body.add("image", image.getResource());
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String url = gatewayUrl + "/reviews";

        restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
    }

    /**
     * 리뷰 수정 메서드
     *
     * @param request 리뷰 정보 객체
     */
    @Override
    public void updateReview(ReviewDto.Request request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ReviewDto.Request> requestEntity = new HttpEntity<>(request, headers);
        String url = gatewayUrl + "/reviews";

        restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                requestEntity,
                String.class);
    }

    @Override
    public ReviewDto.Response getMemberReviews(Long memberId, Long bookId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        String url = gatewayUrl + "/reviews?memberId=" + memberId + "&bookId=" + bookId;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<ReviewDto.Response>() {
                }
        ).getBody();
    }

    @Override
    public PageResponse<ReviewDto.Response> getReviews(Long bookId, int page) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/reviews/book/" + bookId + "?page=" + page;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<PageResponse<ReviewDto.Response>>() {
                }
        ).getBody();
    }
}
