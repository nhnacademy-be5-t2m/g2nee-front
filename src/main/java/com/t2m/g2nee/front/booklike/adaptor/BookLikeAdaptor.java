package com.t2m.g2nee.front.booklike.adaptor;

import com.t2m.g2nee.front.booklike.dto.BookLikeDto;

public interface BookLikeAdaptor {

    /**
     * 좋아요 설정
     *
     * @param request 회원아이디, 책 아이디가 담긴 객체
     * @return BookLikeDto
     */
    BookLikeDto setLike(BookLikeDto request);

    /**
     * 좋아요 갯수
     *
     * @param memberId 회원아이디
     * @return Long
     */
    Long getMemberLikesNum(Long memberId);

}
