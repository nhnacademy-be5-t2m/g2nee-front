package com.t2m.g2nee.front.bookset.book.adaptor;

import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.awt.print.Book;
import java.util.List;

public interface BookGetAdaptor {


    /**
     * 책 하나 정보를 조회하는 서비스 입니다.
     *
     * @param memberId 회원 아이디
     * @param bookId   책 아이디
     * @return BookDto.Response
     */
    BookDto.Response getBook(Long memberId, Long bookId);


    /**
     * 최신 발매된 책 6권을 조회하는 메서드
     *
     * @return List<BookDto.ListResponse>
     */
    List<BookDto.ListResponse> getNewBooks();

    /**
     * 주문량이 많은 6권을 조회하는 메서드
     * @return List<BookDto.ListResponse>
     */
    List<BookDto.ListResponse> getBestseller();

    /**
     * 검색어를 통해 책을 검색하여 조회하는 메서드
     *
     * @param memberId 회원 아이디
     * @param page     페이지 번호
     * @param keyword  검색 키워드
     * @param sort     정렬 조건
     * @param condition 검색 조건
     * @return PageResponse<BookDto.ListResponse>
     */
    PageResponse<BookDto.ListResponse> getBooksBySearch(int page, Long memberId, String keyword, String sort, String condition);

    /**
     * 카테고리별 책 조회 메서드
     *
     * @param memberId   회원 아이디
     * @param page       페이지 번호
     * @param sort       정렬 기준
     * @param categoryId 카테고리 아이디
     * @return PageResponse<BookDto.ListResponse>
     */
    PageResponse<BookDto.ListResponse> getBooksByCategory(int page, Long memberId, String sort, Long categoryId);

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
    PageResponse<BookDto.ListResponse> getBooksBySearchByCategory(int page, Long memberId, String sort, String keyword,
                                                                  Long categoryId);

    /**
     * 현재 책의 카테고리를 기준으로 책을 조회하는 메서드
     *
     * @param categoryIdList 카테고리 아이디 리스트
     * @return PageResponse<BookDto.ListResponse>
     */
    List<BookDto.ListResponse> getRecommendBooks(List<Long> categoryIdList, Long bookId);

    /**
     * 책 재고를 조회하는 메서드
     * @param bookIdList 책 아이디 리스트
     * @return List<BookDto.ListResponse>
     */
    List<BookDto.ListResponse> getBookStock(List<Long> bookIdList);

    /**
     * 회원이 좋아요한 책을 조회하는 메서드
     * @param memberId 회원 아이디
     * @return List<BookDto.ListResponse>
     */
    PageResponse<BookDto.ListResponse> getMemberLikeBook(int page, Long memberId);
}
