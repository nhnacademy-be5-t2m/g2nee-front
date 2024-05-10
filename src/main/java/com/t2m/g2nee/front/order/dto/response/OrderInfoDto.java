package com.t2m.g2nee.front.order.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.t2m.g2nee.front.order.dto.OrderDetailDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class OrderInfoDto {

    private OrderInfoDto(){}

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ListResponse {
        private Long orderId;
        private String orderNumber;
        private Long customerId;
        private LocalDateTime orderDate;
        private List<OrderDetailDto.Response> orderDetailList;
        private OrderState orderState;
        private BigDecimal deliveryFee;
        private BigDecimal orderAmount;
        private String receiverName;
        private String receiverPhoneNumber;
        private String receiveAddress;
        private String zipcode;
        private String detailAddress;
        private String message;
        private String couponName;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {
        private Long orderId;
        private String orderNumber;
        private Long customerId;
        private String customerName;
        private LocalDateTime orderDate;
        private List<OrderDetailDto.Response> orderDetailList;
        private OrderState orderState;
        private BigDecimal deliveryFee;
        private BigDecimal orderAmount;
        private String receiverName;
        private String receiverPhoneNumber;
        private String receiveAddress;
        private String zipcode;
        private String detailAddress;
        private String message;
        private String couponName;
    }

    @Getter
    public enum OrderState{
        WAITING("배송준비"), DELIVERING("배송중"), DELIVERED("배송완료"),
        RETURNING("반품대기"), RETURNED("반품완료"), CANCEL("주문취소"),
        PAYWAITING("결제대기"), ABORTED("결제실패");
        private final String name;

        OrderState(String name) {
            this.name = name;
        }
    }
}
