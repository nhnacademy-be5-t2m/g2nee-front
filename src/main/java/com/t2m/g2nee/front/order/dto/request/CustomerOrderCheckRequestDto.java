package com.t2m.g2nee.front.order.dto.request;

import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 비회원의 주문정보를 확인하기 위한 클래스
 *
 * @author : 정지은
 * @since : 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderCheckRequestDto {

    private String orderId;

     private String password;
}
