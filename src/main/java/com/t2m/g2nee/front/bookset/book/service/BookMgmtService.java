package com.t2m.g2nee.front.bookset.book.service;

import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


/**
 * 책 관리 service 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BookMgmtService {

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

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String url = gatewayUrl + "/shop/books";


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

    public BookDto.Response getBook(Long bookId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        String url = gatewayUrl + "/shop/books/" + bookId;

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

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String url = gatewayUrl + "/shop/books/" + bookId;

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

    public void updateBookStatus(Long bookId, BookDto.Request request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BookDto.Request> requestEntity = new HttpEntity<>(request, headers);
        String url = gatewayUrl + "/shop/books/status/" + bookId;

        restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                requestEntity,
                String.class
        ).getBody();
    }

    public Integer addBookQuantity(Long bookId, int quantity) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        String url = gatewayUrl + "/shop/books/quantity/" + bookId + "?quantity=" + quantity;

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
    public PageResponse<BookDto.ListResponse> getAllBookList(int page) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("page", String.valueOf(page));

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);
        String url = gatewayUrl + "/shop/books/list?page=" + page;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<PageResponse<BookDto.ListResponse>>() {
                }
        ).getBody();
    }

}
