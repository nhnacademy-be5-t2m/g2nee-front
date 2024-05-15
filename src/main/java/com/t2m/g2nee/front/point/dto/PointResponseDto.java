package com.t2m.g2nee.front.point.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 포인트 responseDto 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointResponseDto {

    private int point;
    private LocalDateTime changeDate;
    private ChangeReason reason;
}
