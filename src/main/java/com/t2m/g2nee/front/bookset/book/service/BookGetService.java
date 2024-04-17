package com.t2m.g2nee.front.bookset.book.service;


import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.web.util.UriComponentsBuilder;

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
     * @param bookId 책 아이디
     * @return BookDto.Response
     */
    public BookDto.Response getBook(Long bookId){

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
     * 최신 발매된 책 6권을 조회하는 메서드
     *
     * @return List<BookDto.ListResponse>
     */
    public List<BookDto.ListResponse> getNewBooks() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/shop/books/new";
        // 회원은 삭제된 책을 조회하지 못하게 필터링
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<BookDto.ListResponse>>() {
                }
                ).getBody()
                .stream()
                .filter(book -> !book.getBookStatus().equals(BookDto.BookStatus.DELETED))
                .collect(Collectors.toList());

    }

    /**
     * 검색어를 통해 책을 검색하여 조회하는 메서드
     * @param page 페이지 번호
     * @param keyword 검색 키워드
     * @param sort 정렬 조건
     * @return PageResponse<BookDto.ListResponse>
     */
    public PageResponse<BookDto.ListResponse> getBooksBySearch(int page, String keyword, String sort) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = URLDecoder.decode(UriComponentsBuilder
                .fromHttpUrl(gatewayUrl + "/shop/books/search")
                .queryParam("page",page)
                .queryParam("keyword",keyword)
                .queryParam("sort",sort)
                .toUriString(), StandardCharsets.UTF_8);


        PageResponse<BookDto.ListResponse> responses = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<PageResponse<BookDto.ListResponse>>() {
                        }
                )
                .getBody();

        // 회원은 삭제된 책을 조회하지 못하게 필터링

            List<BookDto.ListResponse> list = responses.getData().stream()
                    .filter(book -> !book.getBookStatus().equals(BookDto.BookStatus.DELETED))
                    .collect(Collectors.toList());
            responses.setData(list);

        return responses;

    }

    /**
     * 카테고리별 책 조회 메서드
     * @param page 페이지 번호
     * @param sort 정렬 기준
     * @param categoryId 카테고리 아이디
     * @return PageResponse<BookDto.ListResponse>
     */
    public PageResponse<BookDto.ListResponse> getBooksByCategory(int page, String sort, Long categoryId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = URLDecoder.decode(UriComponentsBuilder
                .fromHttpUrl(gatewayUrl + "/shop/books/category/" + categoryId)
                .queryParam("page", page)
                .queryParam("sort", sort)
                .toUriString(), StandardCharsets.UTF_8);


        PageResponse<BookDto.ListResponse> responses = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<PageResponse<BookDto.ListResponse>>() {
                        }
                )
                .getBody();

        // 회원은 삭제된 책을 조회하지 못하게 필터링

        List<BookDto.ListResponse> list = responses.getData().stream()
                .filter(book -> !book.getBookStatus().equals(BookDto.BookStatus.DELETED))
                .collect(Collectors.toList());
        responses.setData(list);

        return responses;

    }

    /**
     * 카테고리와 검색어로 책을 검색하는 메서드
     * @param page 페이지 번호
     * @param sort 정렬 기준
     * @param keyword 검색 키워드
     * @param categoryId 카테고리 아이디
     * @return PageResponse<BookDto.ListResponse>
     */
    public PageResponse<BookDto.ListResponse> getBooksBySearchByCategory(int page, String sort, String keyword,
                                                                         Long categoryId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = URLDecoder.decode(UriComponentsBuilder
                .fromHttpUrl(gatewayUrl + "/shop/books/search")
                .queryParam("categoryId", categoryId)
                .queryParam("page", page)
                .queryParam("keyword", keyword)
                .queryParam("sort", sort)
                .toUriString(), StandardCharsets.UTF_8);


        PageResponse<BookDto.ListResponse> responses = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<PageResponse<BookDto.ListResponse>>() {
                        }
                )
                .getBody();

        // 회원은 삭제된 책을 조회하지 못하게 필터링

        List<BookDto.ListResponse> list = responses.getData().stream()
                .filter(book -> !book.getBookStatus().equals(BookDto.BookStatus.DELETED))
                .collect(Collectors.toList());
        responses.setData(list);

        return responses;

    }

}
