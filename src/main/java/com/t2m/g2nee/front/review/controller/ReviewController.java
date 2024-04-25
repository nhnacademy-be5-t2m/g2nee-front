package com.t2m.g2nee.front.review.controller;

import com.t2m.g2nee.front.review.dto.ReviewDto;
import com.t2m.g2nee.front.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 리뷰 controller 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public String postReview(@RequestPart MultipartFile image,
                             @ModelAttribute ReviewDto.Request request) {

        reviewService.postReview(image,request);

        return "redirect:/book" + request.getBookId();
    }

    @PatchMapping("/{reviewId}")
    public String updateReview(@PathVariable("reviewId") Long reviewId,
                               @RequestPart(required = false) MultipartFile image,
                               @ModelAttribute ReviewDto.Request request) {

        request.setReviewId(reviewId);
        reviewService.updateReview(image,request);

        return "redirect:/book" + request.getBookId();

    }

    @GetMapping
    public String getReview(@ModelAttribute ReviewDto.Request request){

        return null;
    }

}
