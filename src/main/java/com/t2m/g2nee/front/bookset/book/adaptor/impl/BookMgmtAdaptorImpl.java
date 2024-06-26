package com.t2m.g2nee.front.bookset.book.adaptor.impl;

import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeFileHttpHeaders;
import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeHttpHeaders;

import com.t2m.g2nee.front.bookset.book.adaptor.BookMgmtAdaptor;
import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class BookMgmtAdaptorImpl implements BookMgmtAdaptor {
    private final RestTemplate restTemplate;
    @Value("${g2nee.gateway}")
    private String gatewayUrl;

    /**
     * 책을 등록하는 메서드
     *
     * @param request   등록할 책 정보가 있는 객체
     * @param thumbnail 썸네일
     * @param details   상세 이미지
     */
    @Override
    public void registerBook(BookDto.Request request, MultipartFile thumbnail, MultipartFile[] details) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("request", request);
        body.add("thumbnail", thumbnail.getResource());
        List<Object> multipartFileList = new ArrayList<>();
        for (MultipartFile detail : details) {
            multipartFileList.add(detail.getResource());
        }
        body.addAll("details", multipartFileList);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, makeFileHttpHeaders());

        String url = gatewayUrl + "/books";


        restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
    }

    /**
     * 책 정보를 가져오는 메서드
     *
     * @param bookId 책 아이디
     * @return BookDto.Response
     */
    @Override
    public BookDto.Response getUpdateBook(Long bookId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(makeHttpHeaders());
        String url = gatewayUrl + "/books/" + bookId + "/update";

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<BookDto.Response>() {
                }
        ).getBody();

    }

    /**
     * 책을 수정하는 메서드 입니다.
     *
     * @param bookId    수정할 책 아이디
     * @param request   수정할 정보가 담긴 객체
     * @param thumbnail 섬네일 이미지
     * @param details   세부 이미지
     */
    @Override
    public void updateBook(Long bookId, BookDto.Request request, MultipartFile thumbnail,
                           MultipartFile[] details) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("request", request);
        body.add("thumbnail", thumbnail.getResource());
        List<Object> multipartFileList = new ArrayList<>();
        for (MultipartFile detail : details) {
            multipartFileList.add(detail.getResource());
        }
        body.addAll("details", multipartFileList);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, makeFileHttpHeaders());
        String url = gatewayUrl + "/books/" + bookId;

        restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                requestEntity,
                String.class
        ).getBody();

    }

    /**
     * 책 상태를 변경하는 메서드 입니다.
     *
     * @param bookId  책 아이디
     * @param request 상태 정보가 담긴 객체
     */
    @Override
    public void updateBookStatus(Long bookId, BookDto.Request request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BookDto.Request> requestEntity = new HttpEntity<>(request, makeHttpHeaders());
        String url = gatewayUrl + "/books/status/" + bookId;

        restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                requestEntity,
                String.class
        ).getBody();
    }

    @Override
    public Integer addBookQuantity(Long bookId, int quantity) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(makeHttpHeaders());
        String url = gatewayUrl + "/books/quantity/" + bookId + "?quantity=" + quantity;

        return restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                requestEntity,
                Integer.class
        ).getBody();
    }

    /**
     * 책 정보들을 조회하는 메서드입니다.
     *
     * @param page 페이지 번호
     * @return PageResponse<BookDto.ListResponse>
     */
    @Override
    public PageResponse<BookDto.ListResponse> getAllBookList(int page) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("page", String.valueOf(page));

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, makeHttpHeaders());
        String url = gatewayUrl + "/books/list?page=" + page;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<PageResponse<BookDto.ListResponse>>() {
                }
        ).getBody();
    }

    @Override
    public PageResponse<BookDto.ListResponse> getBookListByKeyword(String keyword, int page) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<String> requestEntity = new HttpEntity<>(makeHttpHeaders());

        String url = URLDecoder.decode(UriComponentsBuilder
                .fromHttpUrl(gatewayUrl + "/books/search")
                .queryParam("page", page)
                .queryParam("condition", "TITLE")
                .queryParam("keyword", keyword)
                .toUriString(), StandardCharsets.UTF_8);

        return restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<PageResponse<BookDto.ListResponse>>() {
                        }
                )
                .getBody();
    }

}
