package com.t2m.g2nee.front.order.dto.request;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderForm {

    @Pattern(regexp = "^[a-zA-Z가-힣]{2,20}$|^$", message = "영어와 한글만 사용하여 2-20자의 형식으로 작성하여 주십시오.")
    private String name;

    @Pattern(regexp = "^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{8,20}$|^$", message = "영어 숫자, 특수문자를 포함하여 8-20자의 형식으로 작성하여 주십시오.")
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$|^$", message = "올바른 email 형식으로 작성하여 주십시오.")
    private String email;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$|^$", message = "'-'를 제외한 올바른 전화번호형식으로 작성하여 주십시오.")
    private String phoneNumber;

    @NotBlank(message = "수령인은 비울 수 없습니다.")
    @Size(min = 1, max = 50, message = "50자 미만으로 입력해 주세요.")
    private String receiverName;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "'-'를 제외한 올바른 전화번호형식으로 작성하여 주십시오.")
    private String receiverPhoneNumber;

    private String deliveryWishDate;//null 가능
    @NotNull(message = "가격을 비어있을 수 없습니다.")

    @Min(value = 0, message = "가격은 음수가 될 수 없습니다.")
    private int netAmount;   //순수 금액

    @NotNull(message = "가격은 비어있을 수 없습니다.")
    @Min(value = 0, message = "가격은 음수가 될 수 없습니다.")
    private int orderAmount; //주문금액

    @NotNull(message = "배송비는 비어있을 수 없습니다.")
    @Min(value = 0, message = "배송비는 음수가 될 수 없습니다.")
    private int deliveryFee;

    @Size(max = 100, message = "100자 이하로 작성해 주십시오.")
    @NotEmpty(message = "주소를 입력해주십시오.")
    private String receiverAddress;

    @Pattern(regexp = "^(\\d{3}-\\d{3}|\\d{5})$", message = "우편번호의 형식이 올바르지 않습니다.")
    private String zipcode;

    @Size(max = 50, message = "50자 이하로 작성해 주십시오.")
    @NotEmpty(message = "상세주소를 입력해주십시오.")
    private String detailAddress;

    private String message;//null가능

    @NotNull(message = "주문 상세는 비울 수 없습니다.")
    private List<OrderDetailDto> orderDetailList;

    private Long customerId;

    private Long couponId;//null가능

    private Integer point;//null가능
}


