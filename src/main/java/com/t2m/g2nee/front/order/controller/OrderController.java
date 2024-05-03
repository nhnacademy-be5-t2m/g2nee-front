package com.t2m.g2nee.front.order.controller;


import com.t2m.g2nee.front.order.dto.request.AddressInfoDto;
import com.t2m.g2nee.front.order.dto.request.CustomerOrderCheckRequestDto;
import com.t2m.g2nee.front.order.dto.request.OrdererInfoDto;
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
public class OrderController {

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

    /**
     * 바로구매시 bookID, 구매수량과 함께 주문페이지를 띄워주는 메소드
     *
     * @param bookId 구매할 책의 id
     * @param bookCount 구매할 책의 수량
     * @param model 주문 form 에 필요한 dto 객체를 저장할 model
     * @return 주문페이지를 보여준다.
     */
    @GetMapping("/buyNow")
    public String buyNowOrderForm(@RequestParam("bookId") Long bookId,@RequestParam("bookCount") Long bookCount, Model model){
        model.addAttribute("ordererInfo",new OrdererInfoDto());
        model.addAttribute("addressInfo",new AddressInfoDto());
        return "order/orderForm";
    }

}
