package com.t2m.g2nee.front.bookset.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 정보를 반환하는 dto
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryInfoDto {

    private Long categoryId;
    private String categoryName;
    private String categoryEngName;
}
