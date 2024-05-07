package com.t2m.g2nee.front.order.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderListForAdminResponseDto {
    private Long orderId;
    private String orderNumber;
    private Long customerId;
    private LocalDateTime orderDate;
    private String orderState;
    private BigDecimal orderAmount;
    private String receiverName;
    private String receiverPhoneNumber;
    private String receiveAddress;
    private String zipcode;
    private String detailAddress;
    private String message;
    private String couponName;
}
