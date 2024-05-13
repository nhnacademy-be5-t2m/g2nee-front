package com.t2m.g2nee.front.payment.controller;

import com.t2m.g2nee.front.payment.dto.request.TossPaymentRequestDto;
import com.t2m.g2nee.front.payment.dto.response.PaymentInfoDto;
import com.t2m.g2nee.front.payment.service.PaymentService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order/payment")
public class PaymentController {

    private final PaymentService paymentService;
    @Value("${toss.client.api.key}")
    private String tossClientApiKey;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * toss payment 결제
     * @param model
     * @return
     */
    @GetMapping("/toss")
    public String tossPayments(Model model) {
        model.addAttribute("tossClientApiKey", tossClientApiKey);
        return "payment/pg/tossPayment";
    }

    /**
     * 결제 승인 요청
     */
    @GetMapping("/toss/success")
    public String requestTossPayment(RedirectAttributes redirectAttributes,
                                     @RequestParam(value = "orderId") String orderId,
                                     @RequestParam(value = "paymentKey") String paymentKey,
                                     @RequestParam(value = "amount") BigDecimal amount,
                                     @RequestParam(value = "customerId") String customerId,
                                     @RequestParam(value = "point") String point
    ) {
        TossPaymentRequestDto request = new TossPaymentRequestDto(
                orderId, amount, Long.valueOf(customerId), paymentKey, Integer.valueOf(point)
        );

        PaymentInfoDto result = paymentService.requestPayment(request);
        redirectAttributes.addFlashAttribute("paymentSuccess", result);

        return "payment/paymentSuccess";
    }


    /**
     * 결제 실패
     */
    @GetMapping("/fail")
    public String paymentResult(Model model,
                                @RequestParam(value = "message") String message,
                                @RequestParam(value = "code") Integer code
    ) {
        model.addAttribute("code", code);
        model.addAttribute("message", message);

        return "payment/paymentFail";
    }
}