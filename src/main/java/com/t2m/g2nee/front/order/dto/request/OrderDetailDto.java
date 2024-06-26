package com.t2m.g2nee.front.order.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class OrderDetailDto {

    @NotNull(message = "책은 비어있을 수 없습니다.")
    private Long bookId;

    @NotNull(message = "가격을 비어있을 수 없습니다.")
    @Min(value = 0, message = "가격은 음수가 될 수 없습니다.")
    private int price;

    @NotNull(message = "수량은 비어있을 수 없습니다.")
    @Min(value = 0, message = "수량은 음수가 될 수 없습니다.")
    private int quantity;

    @NotNull(message = "포장지는 비어있을 수 없습니다.")
    private Long packageId;

    private Long couponId;//null가능

}