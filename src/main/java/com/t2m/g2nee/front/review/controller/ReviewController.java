package com.t2m.g2nee.front.review.controller;

import com.t2m.g2nee.front.review.dto.ReviewDto;
import com.t2m.g2nee.front.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public String postReview(@RequestPart(required = false) MultipartFile image,
                             @ModelAttribute ReviewDto.Request request) {

        reviewService.postReview(image,request);
        return "redirect:/books/" + request.getBookId();
    }

    @PatchMapping
    public String updateReview(@ModelAttribute ReviewDto.Request request) {

        reviewService.updateReview(request);

        return "redirect:/books/" + request.getBookId();

    }

}
