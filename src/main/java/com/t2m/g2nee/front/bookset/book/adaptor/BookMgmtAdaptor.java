package com.t2m.g2nee.front.bookset.book.adaptor;

import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.utils.PageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface BookMgmtAdaptor {

    /**
     * 책을 등록하는 메서드
     *
     * @param request   등록할 책 정보가 있는 객체
     * @param thumbnail 썸네일
     * @param details   상세 이미지
     */
    void registerBook(BookDto.Request request, MultipartFile thumbnail, MultipartFile[] details);

    /**
     * 책 정보를 가져오는 메서드
     *
     * @param bookId 책 아이디
     * @return BookDto.Response
     */

    BookDto.Response getBook(Long bookId);

    /**
     * 책을 수정하는 메서드 입니다.
     *
     * @param bookId    수정할 책 아이디
     * @param request   수정할 정보가 담긴 객체
     * @param thumbnail 섬네일 이미지
     * @param details   세부 이미지
     */
    void updateBook(Long bookId, BookDto.Request request, MultipartFile thumbnail,
                    MultipartFile[] details);

    /**
     * 책 상태를 변경하는 메서드 입니다.
     *
     * @param bookId  책 아이디
     * @param request 상태 정보가 담긴 객체
     */

    void updateBookStatus(Long bookId, BookDto.Request request);

    //TODO: 나중에

    /**
     * 책 수령 변경 메서드
     *
     * @param bookId   책 아이디
     * @param quantity 책 수량
     * @return 바꾼 수량
     */
    Integer addBookQuantity(Long bookId, int quantity);

    /**
     * 책 정보들을 조회하는 메서드입니다.
     *
     * @param page 페이지 번호
     * @return PageResponse<BookDto.ListResponse>
     */
    PageResponse<BookDto.ListResponse> getAllBookList(int page);
}
