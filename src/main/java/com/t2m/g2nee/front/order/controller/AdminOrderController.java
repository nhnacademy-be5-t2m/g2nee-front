package com.t2m.g2nee.front.order.controller;

import static com.t2m.g2nee.front.order.dto.response.OrderInfoDto.OrderState.DELIVERING;

import com.t2m.g2nee.front.order.dto.OrderDetailDto;
import com.t2m.g2nee.front.order.dto.response.OrderInfoDto;
import com.t2m.g2nee.front.order.service.OrderGetService;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
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

    /**
     * admin이 보는 전체 주문 목록 페이지
     *
     * @param model model
     * @param page  페이지
     * @return 전체 주문 목록 페이지
     */
    @GetMapping("/list")
    public String orderList(Model model, @RequestParam(required = false, defaultValue = "1") int page) {
        PageResponse<OrderInfoDto.ListResponse> orderPage = orderGetService.getAllOrderList(page);
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("orderState", OrderInfoDto.OrderState.values());
        model.addAttribute("orderStates", OrderInfoDto.OrderState.values());

        return "admin/order/adminOrder";
    }

    /**
     * 주문 목록에서 단일 주문 목록 페이지
     *
     * @param orderId 주문 Id
     * @param model   model
     * @return 단일 주문 페이지
     */
    @GetMapping("/{orderId}")
    public String getOrder(@PathVariable("orderId") Long orderId, Model model) {


        OrderInfoDto.Response orderResponse = orderGetService.getOrderById(orderId);
        model.addAttribute("order", orderResponse);
        List<OrderDetailDto.Response> detailResponse = orderGetService.getOrderDetailListByOrderId(orderId);
        model.addAttribute("orderDetails", detailResponse);
        model.addAttribute("orderStates", OrderInfoDto.OrderState.values());

        return "admin/order/adminOrderDetail";
    }

    @GetMapping("/status/{orderId}")
    public String changeOrderState(@PathVariable("orderId") Long orderId) {

        orderGetService.changeOrderStatus(orderId, DELIVERING);

        return "redirect:/admin/orders/list";
    }

}
