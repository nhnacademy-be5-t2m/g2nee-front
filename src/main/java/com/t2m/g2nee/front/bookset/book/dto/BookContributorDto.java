package com.t2m.g2nee.front.bookset.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 책 기여자 dto 입니다.
 *
 * @author : 신동민
 * @since : 1.0
 */
public class BookContributorDto {

    private BookContributorDto() {
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String contributorId;
        private String contributorName;
        private String contributorEngName;
        private String roleName;
    }
}
