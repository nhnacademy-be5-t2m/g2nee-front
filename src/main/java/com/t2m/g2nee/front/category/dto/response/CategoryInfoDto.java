package com.t2m.g2nee.front.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInfoDto {

    //카테고리 정보
    private Long categoryId;
    private String categoryName;
    private String categoryEngName;
}
