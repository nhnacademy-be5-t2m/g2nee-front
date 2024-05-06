package com.t2m.g2nee.front.bookset.book.service;

import com.t2m.g2nee.front.bookset.book.adaptor.BookMgmtAdaptor;
import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.utils.PageResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;


/**
 * 책 관리 service 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Service
@Transactional
public class BookMgmtService {

    private final BookMgmtAdaptor bookMgmtAdaptor;

    public BookMgmtService(BookMgmtAdaptor bookMgmtAdaptor) {
        this.bookMgmtAdaptor = bookMgmtAdaptor;
    }


    /**
     * 책을 등록하는 메서드
     *
     * @param request   등록할 책 정보가 있는 객체
     * @param thumbnail 썸네일
     * @param details   상세 이미지
     */
    public void registerBook(BookDto.Request request, MultipartFile thumbnail, MultipartFile[] details) {


        bookMgmtAdaptor.registerBook(request, thumbnail, details);
    }

    /**
     * 책 정보를 가져오는 메서드
     *
     * @param bookId 책 아이디
     * @return BookDto.Response
     */

    public BookDto.Response getUpdateBook(Long bookId) {

        return bookMgmtAdaptor.getUpdateBook(bookId);

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

        bookMgmtAdaptor.updateBook(bookId, request, thumbnail, details);

    }

    /**
     * 책 상태를 변경하는 메서드 입니다.
     *
     * @param bookId  책 아이디
     * @param request 상태 정보가 담긴 객체
     */

    public void updateBookStatus(Long bookId, BookDto.Request request) {

        bookMgmtAdaptor.updateBookStatus(bookId, request);
    }

    public Integer addBookQuantity(Long bookId, int quantity) {
        return bookMgmtAdaptor.addBookQuantity(bookId, quantity);
    }

    /**
     * 책 정보들을 조회하는 메서드입니다.
     *
     * @param page 페이지 번호
     * @return PageResponse<BookDto.ListResponse>
     */
    public PageResponse<BookDto.ListResponse> getAllBookList(int page) {

        return bookMgmtAdaptor.getAllBookList(page);
    }

    public PageResponse<BookDto.ListResponse> getBookListByKeyword(String keyword, int page){

        return bookMgmtAdaptor.getBookListByKeyword(keyword,page);
    }

}
