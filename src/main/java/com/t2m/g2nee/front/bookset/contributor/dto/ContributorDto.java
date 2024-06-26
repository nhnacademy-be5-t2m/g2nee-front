package com.t2m.g2nee.front.bookset.contributor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ContributorDto {

    private ContributorDto() {
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @Pattern(regexp = "^[가-힣0-9]+$", message = "기여자 한글 이름을 입력해주세요.")
        private String contributorName;
        @Pattern(regexp = "^[A-Za-z0-9]+$", message = "기여자 영문 이름을 입력해주세요")
        private String contributorEngName;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {


        private Long contributorId;
        private String contributorName;
        private String contributorEngName;
    }
}

