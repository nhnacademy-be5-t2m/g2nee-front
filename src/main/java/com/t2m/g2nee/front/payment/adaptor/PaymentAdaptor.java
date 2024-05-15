package com.t2m.g2nee.front.payment.adaptor;

import com.t2m.g2nee.front.payment.dto.request.PaymentRequest;
import com.t2m.g2nee.front.payment.dto.response.PaymentInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;

public interface PaymentAdaptor {

    /**
     * 결제 요청
     */
    PaymentInfoDto requestPayment(PaymentRequest paymentRequest);

    /**
     * 결제 취소
     */
    PaymentInfoDto requestCancelPayment(Long paymentId);

    /**
     * 한 사람의 결제 목록
     */
    PageResponse<PaymentInfoDto> getPayments(Long customerId, int page);

    /**
     * 하나의 결제
     */
    PaymentInfoDto getPayment(Long orderId);
}
