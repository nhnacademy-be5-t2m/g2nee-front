package com.t2m.g2nee.front.bookset.book.service;


import com.t2m.g2nee.front.bookset.book.adaptor.BookGetAdaptor;
import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final BookGetAdaptor bookGetAdaptor;

    /**
     * 책 하나 정보를 조회하는 서비스 입니다.
     *
     * @param bookId 책 아이디
     * @return BookDto.Response
     */
    public BookDto.Response getBook(Long memberId, Long bookId) {

        return bookGetAdaptor.getBook(memberId, bookId);
    }

    /**
     * 최신 발매된 책 6권을 조회하는 메서드
     *
     * @return List<BookDto.ListResponse>
     */
    public List<BookDto.ListResponse> getNewBooks() {

        return bookGetAdaptor.getNewBooks();
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
    public PageResponse<BookDto.ListResponse> getBooksBySearch(int page, Long memberId, String keyword, String sort) {

        return bookGetAdaptor.getBooksBySearch(page, memberId, keyword, sort);

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
    public PageResponse<BookDto.ListResponse> getBooksByCategory(int page, Long memberId, String sort,
                                                                 Long categoryId) {

        return bookGetAdaptor.getBooksByCategory(page, memberId, sort, categoryId);

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
    public PageResponse<BookDto.ListResponse> getBooksBySearchByCategory(int page, Long memberId, String sort,
                                                                         String keyword,
                                                                         Long categoryId) {

        return bookGetAdaptor.getBooksBySearchByCategory(page, memberId, sort, keyword, categoryId);

    }

    /**
     * 현재 책의 카테고리를 기준으로 책을 조회하는 메서드
     *
     * @param categoryIdList 카테고리 아이디 리스트
     * @return PageResponse<BookDto.ListResponse>
     */
    public List<BookDto.ListResponse> getRecommendBooks(List<Long> categoryIdList, Long bookId) {

        return bookGetAdaptor.getRecommendBooks(categoryIdList, bookId);
    }
}
