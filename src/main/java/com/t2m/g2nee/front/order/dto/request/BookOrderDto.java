package com.t2m.g2nee.front.order.dto.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookOrderDto {
    private Long bookId;
    private String title;
    private int count;
    private BigDecimal rewardRate;
    private int reward;
    private int originPrice;
    private int bookSale;
    private int couponSale;
    private int finalPrice;
}
