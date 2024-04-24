package com.t2m.g2nee.front.booklike.adaptor;

import com.t2m.g2nee.front.booklike.dto.BookLikeDto;

public interface BookLikeAdaptor {

    /**
     * 좋아요 설정
     * @param request 회원아이디, 책 아이디가 담긴 객체
     */
    void setLike(BookLikeDto request);

}
