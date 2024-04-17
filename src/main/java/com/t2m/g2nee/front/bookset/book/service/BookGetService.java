package com.t2m.g2nee.front.bookset.book.service;


import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * 책 조회 service 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookGetService {

    private final RestTemplate restTemplate;
    @Value("${g2nee.gateway}")
    private String gatewayUrl;

    /**
     * 책 하나 정보를 조회하는 서비스 입니다.
     *
     * @param bookId 책 아이디
     * @return BookDto.Response
     */
    public BookDto.Response getBook(Long bookId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "shop/books/" + bookId;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<BookDto.Response>() {
                }
        ).getBody();
    }

}
