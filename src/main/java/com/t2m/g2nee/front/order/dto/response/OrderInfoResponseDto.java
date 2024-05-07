package com.t2m.g2nee.front.order.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderInfoResponseDto {
    private Long orderId;
    private String orderNumber;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryWishDate;
    //todo: 배송완료날짜 추가해야 함
    private BigDecimal deliveryFee;
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
