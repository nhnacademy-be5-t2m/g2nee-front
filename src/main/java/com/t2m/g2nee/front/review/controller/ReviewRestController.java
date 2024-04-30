package com.t2m.g2nee.front.review.controller;

import com.t2m.g2nee.front.review.dto.ReviewDto;
import com.t2m.g2nee.front.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * fetch용 review restcontroller 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewRestController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<ReviewDto.Response> getReview(@RequestBody ReviewDto.Request request) {
        ReviewDto.Response review = reviewService.getReview(request);

        return ResponseEntity.ok().body(review);
    }
}
