package com.t2m.g2nee.front.order.controller;


import static com.t2m.g2nee.front.aop.MemberAspect.MEMBER_INFO;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.order.dto.response.OrderForPaymentDto;
import com.t2m.g2nee.front.order.service.OrderGetService;
import com.t2m.g2nee.front.payment.service.PaymentService;
import com.t2m.g2nee.front.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 주문과 관련된 controller 입니다.
 *
 * @author : 정지은
 * @since : 1.0
 */
@Controller
@RequestMapping("/mypage/order")
@RequiredArgsConstructor
public class MemberOrderController {
    private final OrderGetService orderGetService;
    private final MemberAspect memberAspect;
    private final PaymentService paymentService;


    @GetMapping("/list")
    @Member
    public String orderList(Model model, @RequestParam(required = false, defaultValue = "1") int page) {
        MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
        Long memberId = null;
        if (member != null) {
            memberId = member.getMemberId();
        }
        PageResponse<OrderForPaymentDto> orderPage = orderGetService.getOrderListForMembers(memberId, page);
        model.addAttribute("orderPage", orderPage);


        return "order/orderList";
    }

    @GetMapping("/{orderId}")
    @Member
    public String getOrder(@PathVariable("orderId") Long orderId, Model model) {
        model.addAttribute("order", orderGetService.getOrderById(orderId));
        model.addAttribute("orderDetails", orderGetService.getOrderDetailListByOrderId(orderId));
        model.addAttribute("orderName", orderGetService.getOrderName(orderId));
        model.addAttribute("payment", paymentService.getPayment(orderId));

        return "order/orderDetail";
    }
}
