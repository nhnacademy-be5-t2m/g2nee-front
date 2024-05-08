package com.t2m.g2nee.front.order.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDto {
    private Long orderDetailId;
    private BigDecimal price;
    private Integer quantity;
    private Boolean isCancelled;
    private String bookName;
    private String packageName;
    private String couponName;
}
