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
@RequestMapping("/orders/payments")
public class PaymentController {

    private final PaymentService paymentService;
    @Value("${toss.client.api.key}")
    private String tossClientApiKey;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * 결제 선택 창
     * ("/{orderId}")
     *
     * @return
     * @PathVariable("orderId") Long orderId
     */
    @GetMapping
    public String paymentSelect(Model model) {
        //TODO: 주문 정보 + 주문 세부 정보 넘겨주기 필요

        return "payment/paymentSelect";
    }

    /**
     * toss payment
     * ("/{orderId}")
     *
     * @PathVariable("orderId") Long orderId
     */

    @GetMapping("/toss")
    public String tossPayments(Model model) {
        //TODO: 주문 정보 넘겨주기 필요

        model.addAttribute("tossClientApiKey", tossClientApiKey);
        return "payment/pg/tossPayment";
    }

    /**
     * 결제 승인 요청
     */
    @GetMapping("/toss/request")
    public String requestTossPayment(RedirectAttributes redirectAttributes,
                                     @RequestParam(value = "orderId") String orderId,
                                     @RequestParam(value = "paymentKey") String paymentKey,
                                     @RequestParam(value = "amount") BigDecimal amount
    ) {
        TossPaymentRequestDto request = new TossPaymentRequestDto(
                orderId, amount, 7L, paymentKey,
        );
        //TODO: 정보 받아올 수 있는 id 얻는 방법 있는지 물어보기

        PaymentInfoDto result = paymentService.requestPayment(request);
        redirectAttributes.addFlashAttribute("paymentSuccess", result);

        return "redirect:/orders/payments/success";
    }

    /**
     * 결제 성공
     */
    @GetMapping("/success")
    public String requestPayment() {
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