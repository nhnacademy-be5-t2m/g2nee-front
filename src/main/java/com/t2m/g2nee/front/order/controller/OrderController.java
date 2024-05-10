package com.t2m.g2nee.front.order.controller;



import com.t2m.g2nee.front.order.dto.OrderDetailDto;
import com.t2m.g2nee.front.order.dto.request.CustomerOrderCheckRequestDto;
import com.t2m.g2nee.front.order.dto.response.OrderInfoDto;
import com.t2m.g2nee.front.order.service.OrderGetService;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 주문과 관련된 controller 입니다.
 *
 * @author : 정지은
 * @since : 1.0
 */
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderGetService orderGetService;

    /**
     * 비회원의 주문조회 정보를 받는 페이지
     *
     * @param model 모델
     * @return 비회원주문조회 로그인 페이지
     */
    @GetMapping("/customLogin")
    public String customerLogin(Model model) {
        model.addAttribute("customerOrderInfo", new CustomerOrderCheckRequestDto());
        return "order/customerLogin";
    }

    /**
     * 비회원 주문정보로 통신후 성공페이지를 띄워주는 메소드.
     *
     * @param request 비회원 주문조회에 필요한 request 정보
     * @param model   비회원 정보가 일치하는지 저장할 model
     * @return 성공, 실패 페이지를 보여준다.
     */
    @PostMapping("/customLogin")
    public String customerLoginComplete(@ModelAttribute("customerOrderInfo") CustomerOrderCheckRequestDto request,
                                        Model model) {
        //TODO : shop server의 order쪽으로 보내기
        return "member/customerLogin";
    }

//    @GetMapping("/list")
//    public String orderList(Model model, @RequestParam(required = false, defaultValue = "1") int page){
//        PageResponse<OrderInfoDto.ListResponse> orderPage = orderGetService.getAllOrderList(page);
//        model.addAttribute("orderPage", orderPage);
//
//        return "admin/order/adminOrder";
//    }
    @GetMapping("/{orderId}")
    public String getOrder(@PathVariable("orderId") Long orderId, Model model){


        OrderInfoDto.Response orderResponse = orderGetService.getOrderById(orderId);
        model.addAttribute("order", orderResponse);
        List<OrderDetailDto.Response> detailResponse = orderGetService.getOrderDetailListByOrderId(orderId);
        model.addAttribute("orderDetails", detailResponse);

        return "admin/order/adminOrderDetail";
    }

    @GetMapping("/{orderNumber}")
    public String getOrderForNonMember(@PathVariable("orderNumber") String orderNumber, Model model){
        OrderInfoDto.Response orderResponse = orderGetService.getOrderByNumber(orderNumber);
        model.addAttribute("order", orderResponse);

        return "order/orderDetail";
    }
}
