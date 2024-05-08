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
}
