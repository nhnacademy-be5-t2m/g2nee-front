package com.t2m.g2nee.front.order.controller;

import com.t2m.g2nee.front.member.service.MemberService;
import com.t2m.g2nee.front.order.dto.request.CustomerOrderCheckRequestDto;
import com.t2m.g2nee.front.order.dto.response.OrderInfoDto;
import com.t2m.g2nee.front.order.service.OrderGetService;
import com.t2m.g2nee.front.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderGetService orderGetService;
//    private final MemberService memberService;

    @GetMapping("/list")
    public String orderList(Model model, @RequestParam(required = false, defaultValue = "1") int page){
        PageResponse<OrderInfoDto.ListResponse> orderPage = orderGetService.getAllOrderList(page);
        model.addAttribute("orderPage", orderPage);

        return "admin/order/adminOrder";
    }

    @GetMapping("/{orderId}")
    public String getOrder(@PathVariable("orderId") Long orderId, Model model){

        OrderInfoDto.ListResponse listResponse = orderGetService.getOrderById(null, orderId);
        model.addAttribute("book", listResponse);
        return "admin/order/orderDetail";
    }


}
