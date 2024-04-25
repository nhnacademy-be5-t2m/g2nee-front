package com.t2m.g2nee.front.booklike.service;

import com.t2m.g2nee.front.booklike.adaptor.BookLikeAdaptor;
import com.t2m.g2nee.front.booklike.dto.BookLikeDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookLikeService {

    private final BookLikeAdaptor bookLikeAdaptor;


    public BookLikeService(BookLikeAdaptor bookLikeAdaptor) {
        this.bookLikeAdaptor = bookLikeAdaptor;
    }

    public BookLikeDto setLike(BookLikeDto request) {
        return bookLikeAdaptor.setLike(request);
    }

    public Long getMemberLikesNum(Long memberId) {

        // 비회원은 항상 0
        if (memberId == null) {
            return 0L;
        } else {
        // 회원 일때만 메서드 실행
            return bookLikeAdaptor.getMemberLikesNum(memberId);
        }
    }

}
