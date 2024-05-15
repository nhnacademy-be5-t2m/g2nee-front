package com.t2m.g2nee.front.review.adaptor;

import com.t2m.g2nee.front.review.dto.ReviewDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewAdaptor {

    /**
     * 리뷰 작성 메서드
     *
     * @param image   이미지
     * @param request 리뷰 정보 객체
     */
    void postReview(MultipartFile image, ReviewDto.Request request);

    /**
     * 리뷰 수정 메서드
     *
     * @param request 리뷰 정보 객체
     */
    void updateReview(ReviewDto.Request request);

    ReviewDto.Response getMemberReviews(Long memberId, Long bookId);

    /**
     * 책 리뷰를 조회하는 메서드
     *
     * @param bookId 책 아이디
     * @return PageResponse<ReviewDto.Response>
     */
    PageResponse<ReviewDto.Response> getReviews(Long bookId, int page);


}
