package com.t2m.g2nee.front.bookset.book.adaptor;

import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;

public interface BookGetAdaptor {


    /**
     * 책 하나 정보를 조회하는 서비스 입니다.
     *
     * @param bookId 책 아이디
     * @return BookDto.Response
     */
     BookDto.Response getBook(Long bookId);


    /**
     * 최신 발매된 책 6권을 조회하는 메서드
     *
     * @return List<BookDto.ListResponse>
     */
     List<BookDto.ListResponse> getNewBooks();

    /**
     * 검색어를 통해 책을 검색하여 조회하는 메서드
     *
     * @param page    페이지 번호
     * @param keyword 검색 키워드
     * @param sort    정렬 조건
     * @return PageResponse<BookDto.ListResponse>
     */
     PageResponse<BookDto.ListResponse> getBooksBySearch(int page, String keyword, String sort);

    /**
     * 카테고리별 책 조회 메서드
     *
     * @param page       페이지 번호
     * @param sort       정렬 기준
     * @param categoryId 카테고리 아이디
     * @return PageResponse<BookDto.ListResponse>
     */
     PageResponse<BookDto.ListResponse> getBooksByCategory(int page, String sort, Long categoryId);

    /**
     * 카테고리와 검색어로 책을 검색하는 메서드
     *
     * @param page       페이지 번호
     * @param sort       정렬 기준
     * @param keyword    검색 키워드
     * @param categoryId 카테고리 아이디
     * @return PageResponse<BookDto.ListResponse>
     */
     PageResponse<BookDto.ListResponse> getBooksBySearchByCategory(int page, String sort, String keyword,
                                                                         Long categoryId);

    /**
     * 현재 책의 카테고리를 기준으로 책을 조회하는 메서드
     *
     * @param categoryIdList 카테고리 아이디 리스트
     * @return PageResponse<BookDto.ListResponse>
     */
     List<BookDto.ListResponse> getRecommendBooks(List<Long> categoryIdList, Long bookId);
}
