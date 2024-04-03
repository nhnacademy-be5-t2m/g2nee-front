package com.t2m.g2nee.front.ErrorHandler.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 기본 회원정보의 응답을 받는 response
 *
 * @author : 정지은
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
}
