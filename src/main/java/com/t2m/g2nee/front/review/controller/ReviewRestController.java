package com.t2m.g2nee.front.review.controller;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.review.dto.ReviewDto;
import com.t2m.g2nee.front.review.service.ReviewService;
import com.t2m.g2nee.front.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * fetch용 review restcontroller 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewRestController {

    private final ReviewService reviewService;
    private final MemberAspect memberAspect;

    @Member
    @GetMapping
    public ResponseEntity<Boolean> getReview(@RequestParam Long bookId) {

        Long memberId = (Long) memberAspect.getThreadLocal().get();

        ReviewDto.Response review = reviewService.getReview(memberId, bookId);

        if (review == null) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.ok().body(false);
        }
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<PageResponse<ReviewDto.Response>> getReviews(@PathVariable Long bookId,
                                                                       @RequestParam(defaultValue = "1") int page) {
        PageResponse<ReviewDto.Response> response = reviewService.getReviews(bookId, page);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
