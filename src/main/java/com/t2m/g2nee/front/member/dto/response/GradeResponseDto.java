package com.t2m.g2nee.front.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GradeResponseDto {
    private Long totalAmount;
    private String grade;
}
