package com.t2m.g2nee.front.bookset.book.adaptor.impl;

import com.t2m.g2nee.front.bookset.book.adaptor.BookGetAdaptor;
import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class BookGetAdaptorImpl implements BookGetAdaptor {

    private final RestTemplate restTemplate;
    @Value("${g2nee.gateway}")
    private String gatewayUrl;

    /**
     * 책 하나 정보를 조회하는 서비스 입니다.
     *
     * @param memberId 회원 아이디
     * @param bookId   책 아이디
     * @return BookDto.Response
     */
    @Override
    public BookDto.Response getBook(Long memberId, Long bookId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);


        String url = URLDecoder.decode(UriComponentsBuilder
                .fromHttpUrl(gatewayUrl + "/books/" + bookId)
                .queryParam("memberId", memberId)
                .toUriString());

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
    @Override
    public List<BookDto.ListResponse> getNewBooks() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/books/new";
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
     *
     * @param memberId 회원 아이디
     * @param page     페이지 번호
     * @param keyword  검색 키워드
     * @param sort     정렬 조건
     * @return PageResponse<BookDto.ListResponse>
     */
    @Override
    public PageResponse<BookDto.ListResponse> getBooksBySearch(int page, Long memberId, String keyword, String sort,
                                                               String condition) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = URLDecoder.decode(UriComponentsBuilder
                .fromHttpUrl(gatewayUrl + "/books/search")
                .queryParam("memberId", memberId)
                .queryParam("page", page)
                .queryParam("keyword", keyword)
                .queryParam("sort", sort)
                .queryParam("condition", condition)
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
     *
     * @param memberId   회원 아이디
     * @param page       페이지 번호
     * @param sort       정렬 기준
     * @param categoryId 카테고리 아이디
     * @return PageResponse<BookDto.ListResponse>
     */
    @Override
    public PageResponse<BookDto.ListResponse> getBooksByCategory(int page, Long memberId, String sort,
                                                                 Long categoryId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = URLDecoder.decode(UriComponentsBuilder
                .fromHttpUrl(gatewayUrl + "/books/category/" + categoryId)
                .queryParam("page", page)
                .queryParam("sort", sort)
                .queryParam("memberId", memberId)
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
     *
     * @param memberId   회원 아이디
     * @param page       페이지 번호
     * @param sort       정렬 기준
     * @param keyword    검색 키워드
     * @param categoryId 카테고리 아이디
     * @return PageResponse<BookDto.ListResponse>
     */
    @Override
    public PageResponse<BookDto.ListResponse> getBooksBySearchByCategory(int page, Long memberId, String sort,
                                                                         String keyword,
                                                                         Long categoryId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = URLDecoder.decode(UriComponentsBuilder
                .fromHttpUrl(gatewayUrl + "/books/search")
                .queryParam("memberId", memberId)
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

    /**
     * 현재 책의 카테고리를 기준으로 책을 조회하는 메서드
     *
     * @param categoryIdList 카테고리 아이디 리스트
     * @return PageResponse<BookDto.ListResponse>
     */
    @Override
    public List<BookDto.ListResponse> getRecommendBooks(List<Long> categoryIdList, Long bookId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        String categoryIds = categoryIdList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        String url =
                gatewayUrl + "/books/" + bookId + "/recommend?categoryIdList=" + categoryIds;

        // 회원은 삭제된 책을 조회하지 못하게 필터링
        return Objects.requireNonNull(restTemplate.exchange(
                                url,
                                HttpMethod.GET,
                                requestEntity,
                                new ParameterizedTypeReference<List<BookDto.ListResponse>>() {
                                }
                        )
                        .getBody())
                .stream()
                .filter(book -> !book.getBookStatus().equals(BookDto.BookStatus.DELETED))
                .collect(Collectors.toList());

    }

    @Override
    public List<BookDto.ListResponse> getBookStock(List<Long> bookIdList) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        String bookIds = bookIdList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        String url =
                gatewayUrl + "/books/stock?bookIdList=" + bookIds;

        return restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<List<BookDto.ListResponse>>() {
                        }
                )
                .getBody();
    }

    @Override
    public PageResponse<BookDto.ListResponse> getMemberLikeBook(int page, Long memberId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        String url =
                gatewayUrl + "/books/like/member/" + memberId + "?page=" + page;

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
