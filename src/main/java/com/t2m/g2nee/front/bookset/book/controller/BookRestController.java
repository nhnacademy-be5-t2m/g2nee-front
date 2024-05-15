package com.t2m.g2nee.front.bookset.book.controller;

import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.bookset.book.service.BookGetService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookRestController {

    private final BookGetService bookGetService;

    @PostMapping("/stock")
    public ResponseEntity<List<BookDto.ListResponse>> getBookExceedStock(
            @RequestBody List<BookDto.ListResponse> bookList) {
        List<BookDto.ListResponse> responses = bookGetService.getBookExceedStock(bookList);

        return ResponseEntity.status(HttpStatus.OK).body(responses);

    }
}
