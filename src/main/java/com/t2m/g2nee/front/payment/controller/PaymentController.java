package com.t2m.g2nee.front.payment.controller;

import static com.t2m.g2nee.front.aop.MemberAspect.MEMBER_INFO;
import static com.t2m.g2nee.front.utils.CookieUtil.deleteCookie;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.payment.dto.request.PaymentRequestDto;
import com.t2m.g2nee.front.payment.dto.request.TossPaymentRequestDto;
import com.t2m.g2nee.front.payment.dto.response.PaymentInfoDto;
import com.t2m.g2nee.front.payment.service.PaymentService;
import com.t2m.g2nee.front.shoppingcart.service.ShoppingCartService;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order/payment")
public class PaymentController {

    private final PaymentService paymentService;
    @Value("${toss.client.api.key}")
    private String tossClientApiKey;
    private final MemberAspect memberAspect;
    private final ShoppingCartService shoppingCartService;


    /**
     * toss payment 결제
     *
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
    @Member
    public String requestTossPayment(Model model,
                                     @RequestParam(value = "orderId") String orderId,
                                     @RequestParam(value = "paymentKey") String paymentKey,
                                     @RequestParam(value = "amount") BigDecimal amount,
                                     @RequestParam(value = "customerId") String customerId,
                                     @RequestParam(value = "point") String point,
                                     HttpServletResponse httpServletResponse
    ) {
        TossPaymentRequestDto request = new TossPaymentRequestDto(
                orderId, amount, Long.valueOf(customerId), paymentKey, point.equals("") ? null : Integer.valueOf(point)
        );

        PaymentInfoDto result = paymentService.requestPayment(request);
        model.addAttribute("paymentSuccess", result);

        String memberId = null;
        MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
        if (member != null) {
            memberId = member.getMemberId().toString();
        }
        shoppingCartService.deleteCart(memberId, httpServletResponse);
        deleteCookie(httpServletResponse, "cart");
        return "payment/paymentSuccess";
    }

    @PostMapping("/point")
    @Member
    public String pointPayment(@ModelAttribute("form") PaymentRequestDto request, Model model,
                               HttpServletResponse httpServletResponse) {
        PaymentInfoDto result = paymentService.requestPayment(request);
        model.addAttribute("paymentSuccess", result);

        String memberId = null;
        MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
        if (member != null) {
            memberId = member.getMemberId().toString();
        }
        shoppingCartService.deleteCart(memberId, httpServletResponse);
        deleteCookie(httpServletResponse, "cart");
        return "payment/pointPaySuccess";
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


    /**
     * 결제 취소
     */
    @PostMapping("/{paymentId}")
    public String paymentCancel(@PathVariable("paymentId") Long paymentId,
                                @RequestParam("orderId") Long orderId){
        paymentService.requestCancelPayment(paymentId);
        return "redirect:/mypage/order/"+orderId;
    }
}