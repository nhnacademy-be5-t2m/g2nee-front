package com.t2m.g2nee.front.bookset.book.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.t2m.g2nee.front.bookset.tag.dto.TagDto;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 책 dto 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
public class BookDto {

    private BookDto() {
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @Pattern(regexp = "^[가-힣 0-9a-zA-Z!@#$%^&*(),.?\":{}|<>]+$", message = "책 이름을 입력해주세요.")
        private String title;
        @Pattern(regexp = "^[A-Za-z 0-9]+$", message = "책 영문 이름을 입력해주세요")
        private String engTitle;
        @NotNull(message = "목차를 입력해주세요")
        private String bookIndex;
        @NotNull(message = "설명은 공백 없이 입력해주세요")
        private String description;
        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate publishedDate;
        @Pattern(regexp = "\\d+", message = "가격은 숫자로 입력해주세요")
        private int price;
        @Pattern(regexp = "\\d+", message = "가격은 숫자로 입력해주세요")
        private int salePrice;
        @NotNull
        private String isbn;
        @Pattern(regexp = "\\d+", message = "쪽수는 숫자로 입력해주세요")
        private int pages;
        @Pattern(regexp = "\\d+", message = "수량은 숫자로 입력해주세요")
        private int quantity;
        private BookStatus bookStatus;
        private Long publisherId;
        private List<Long> contributorIdList;
        private List<Long> roleIdList;
        private List<Long> categoryIdList;
        private List<Long> tagIdList;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {

        private Long bookId;
        private List<String> detailImageUrl;
        private int quantity;
        private String title;
        private String engTitle;
        private String bookIndex;
        private String description;
        private LocalDate publishedDate;
        private int price;
        private int salePrice;
        private String isbn;
        private int viewCount;
        private BookStatus bookStatus;
        private int pages;
        private List<BookContributorDto.Response> contributorRoleList;
        private List<List<CategoryInfoDto>> categoryList;
        private List<TagDto.Response> tagList;
        private String publisherName;
        private String publisherEngName;
        private boolean isLiked;
        private Long reviewCount;
        private Double scoreAverage;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ListResponse {

        private Long bookId;
        private String thumbnailImageUrl;
        private String title;
        private String engTitle;
        private LocalDate publishedDate;
        private int price;
        private int salePrice;
        private int viewCount;
        private int quantity;
        private String publisherName;
        private String publisherEngName;
        private BookStatus bookStatus;
        private List<BookContributorDto.Response> contributorRoleList;
        private boolean isLiked;
        private Long reviewCount;
        private Double scoreAverage;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StatusResponse {
        private BookStatus status;
    }

    @Getter
    public enum BookStatus {


        ONSALE("정상판매"), SOLDOUT("매진"), OUTOFPRINT("절판"), DELETED("삭제");

        private final String status;

        BookStatus(String status) {
            this.status = status;
        }
    }

    @Getter
    public enum Sort {

        VIEWCOUNT("인기도 순"), PUBLISHEDDATE("출간일 순"), SALEPRICEASC("가격 낮은 순"),
        SALEPRICEDESC("가격 높은 순"),
        SCORE("평점 순"), REVIEW("리뷰 순");

        private final String value;

        Sort(String value) {
            this.value = value;
        }
    }
}

