package com.t2m.g2nee.front.bookset.book.controller;

import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.bookset.book.service.BookGetService;
import com.t2m.g2nee.front.bookset.book.service.BookMgmtService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookRestController {

    private final BookGetService bookGetService;
    private final BookMgmtService bookMgmtService;

    @PostMapping("/checkStock")
    public ResponseEntity<List<BookDto.ListResponse>> getBookExceedStock(
            @RequestBody List<BookDto.ListResponse> bookList) {
        List<BookDto.ListResponse> responses = bookGetService.getBookExceedStock(bookList);

        return ResponseEntity.status(HttpStatus.OK).body(responses);

    }

    @PostMapping("/checkPrice")
    public ResponseEntity<List<BookDto.ListResponse>> getModifiedPriceBook(
            @RequestBody List<BookDto.ListResponse> bookList) {

        List<BookDto.ListResponse> responses = bookGetService.getModifiedPriceBook(bookList);
        return ResponseEntity.status(HttpStatus.OK).body(responses);

    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<Integer> modifyBookQuantity(@PathVariable Long bookId, @RequestParam int quantity){

        Integer modifiedQuantity = bookMgmtService.addBookQuantity(bookId,quantity);

        return ResponseEntity.ok(modifiedQuantity);
    }
}
