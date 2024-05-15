package com.t2m.g2nee.front.payment.controller;

import static com.t2m.g2nee.front.aop.MemberAspect.MEMBER_INFO;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.payment.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mypage/payments")
public class PaymentMyPageController {

    private final PaymentService paymentService;

    private final MemberAspect memberAspect;

    public PaymentMyPageController(PaymentService paymentService, MemberAspect memberAspect) {
        this.paymentService = paymentService;
        this.memberAspect = memberAspect;
    }

    @Member
    @GetMapping
    public String getMypayments(@RequestParam(defaultValue = "1") int page, Model model) {
        MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
        Long memberId = null;
        if (member != null) {
            memberId = member.getMemberId();
        }

        model.addAttribute("payments", paymentService.getPayments(memberId, page));
        return "mypage/paymentPage";
    }
}
