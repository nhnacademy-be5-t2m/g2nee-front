package com.t2m.g2nee.front.order.controller;

import com.t2m.g2nee.front.member.service.MemberService;
import com.t2m.g2nee.front.order.dto.response.OrderListForAdminResponseDto;
import com.t2m.g2nee.front.order.service.OrderGetService;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class OrderAdminController {
    private final OrderGetService orderGetService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String orderList(Model model, @RequestParam(required = false, defaultValue = "1") int page){
        PageResponse<OrderListForAdminResponseDto> adminPage = orderGetService.getAllOrderList(page);
        model.addAttribute("adminPage", adminPage);

        return "admin/adminOrder";
    }
    @GetMapping("/order-testing")
    public String ordertest(){
        return "admin/adminOrder";
    }


}
