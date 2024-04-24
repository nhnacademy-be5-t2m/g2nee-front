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

    public void setLike(BookLikeDto request){
        bookLikeAdaptor.setLike(request);
    }


}
