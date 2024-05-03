package com.t2m.g2nee.front.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressInfoDto {
    private String zipcode;
    private String address;
    private String addressDetail;
}
