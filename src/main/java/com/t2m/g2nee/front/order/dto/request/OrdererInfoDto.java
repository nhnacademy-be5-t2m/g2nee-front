package com.t2m.g2nee.front.order.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdererInfoDto {
    @NotEmpty(message="이름을 작성하여 주십시오.")
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "올바른 email 형식으로 작성하여 주십시오.")
    private String email;
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "'-'를 제외한 올바른 전화번호형식으로 작성하여 주십시오.")
    private String phoneNumber;
}
