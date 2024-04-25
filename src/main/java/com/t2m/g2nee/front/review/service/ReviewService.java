package com.t2m.g2nee.front.review.service;

import com.t2m.g2nee.front.review.adaptor.ReviewAdaptor;
import com.t2m.g2nee.front.review.dto.ReviewDto;
import com.t2m.g2nee.front.utils.PageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 리뷰 service 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Service
@Transactional
public class ReviewService {

    private final ReviewAdaptor reviewAdaptor;

    public ReviewService(ReviewAdaptor reviewAdaptor) {
        this.reviewAdaptor = reviewAdaptor;
    }

    /**
     * 리뷰 작성 메서드
     * @param image 이미지
     * @param request 리뷰 정보 객체
     */
    public void postReview(MultipartFile image, ReviewDto.Request request) {
       reviewAdaptor.postReview(image,request);
    }

    /**
     * 리뷰 수정 메서드
     * @param image 이미지
     * @param request 리뷰 정보 객체
     */
    public void updateReview(MultipartFile image, ReviewDto.Request request) {
        reviewAdaptor.updateReview(image,request);
    }

    /**
     * 리뷰 조회 메서드
     * @param bookId 책 아이디
     */

    public PageResponse<ReviewDto.Response> getReviews(Long bookId){

        return reviewAdaptor.getReviews(bookId);
    }

    public ReviewDto.Response getReview(ReviewDto.Request request) {

        return reviewAdaptor.getReview(request);
    }
}
