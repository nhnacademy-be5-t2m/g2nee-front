package com.t2m.g2nee.front.review.adaptor;

import com.t2m.g2nee.front.review.dto.ReviewDto;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewAdaptor {

    /**
     * 리뷰 작성 메서드
     * @param image 이미지
     * @param request 리뷰 정보 객체
     */
    void postReview(MultipartFile image, ReviewDto.Request request);
    /**
     * 리뷰 수정 메서드
     * @param request 리뷰 정보 객체
     */
    void updateReview(ReviewDto.Request request);
    /**
     * 리뷰 삭제 메서드
     * @param reviewId 리뷰 아이디
     */
    void deleteReview(Long reviewId);
}
