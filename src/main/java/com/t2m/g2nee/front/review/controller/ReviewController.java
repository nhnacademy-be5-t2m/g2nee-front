package com.t2m.g2nee.front.review.controller;

import com.t2m.g2nee.front.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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


}
