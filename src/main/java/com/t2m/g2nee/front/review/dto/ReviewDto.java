package com.t2m.g2nee.front.review.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * 리뷰 dto 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
public class ReviewDto {

    private ReviewDto(){}
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request{

        private Long reviewId;
        private String content;
        private int score;
        private Long bookId;
        private Long memberId;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response{

        private Long reviewId;
        private String content;
        private String imageUrl;
        private int score;
        private String nickname;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}