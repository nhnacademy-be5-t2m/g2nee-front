package com.t2m.g2nee.front.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class OrderDetailDto {

    private OrderDetailDto(){}

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response{
        private Long orderDetailId;
        private BigDecimal price;
        private Integer quantity;
        private Boolean isCancelled;
        private String bookName;
        private String packageName;
        private String couponName;
    }
}
