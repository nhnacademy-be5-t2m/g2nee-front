package com.t2m.g2nee.front.payment.service.impl;

import com.t2m.g2nee.front.payment.adaptor.PaymentAdaptor;
import com.t2m.g2nee.front.payment.dto.request.PaymentRequest;
import com.t2m.g2nee.front.payment.dto.response.PaymentInfoDto;
import com.t2m.g2nee.front.payment.service.PaymentService;
import com.t2m.g2nee.front.utils.PageResponse;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentAdaptor adaptor;

    public PaymentServiceImpl(PaymentAdaptor adaptor) {
        this.adaptor = adaptor;
    }

    @Override
    public PaymentInfoDto requestPayment(PaymentRequest paymentRequest) {
        return adaptor.requestPayment(paymentRequest);
    }

    @Override
    public PaymentInfoDto requestCancelPayment(Long paymentId) {
        return adaptor.requestCancelPayment(paymentId);
    }

    @Override
    public PageResponse<PaymentInfoDto> getPayments(Long customerId, int page) {
        return adaptor.getPayments(customerId, page);
    }

    @Override
    public PaymentInfoDto getPayment(Long paymentId) {
        return adaptor.getPayment(paymentId);
    }
}
