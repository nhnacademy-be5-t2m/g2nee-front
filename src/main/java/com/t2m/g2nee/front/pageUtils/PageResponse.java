package com.t2m.g2nee.front.pageUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PageResponse를 받는 dto 객체입니다.
 * @param <T> 데이터
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponse<T> {

    private List<T> data;
    private int currentPage;
    private int startPage;
    private int endPage;
    private int totalPage;
    private Long totalElements;
}

