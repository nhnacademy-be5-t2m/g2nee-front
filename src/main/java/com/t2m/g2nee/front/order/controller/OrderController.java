package com.t2m.g2nee.front.order.controller;


import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.bookset.book.service.BookGetService;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.order.dto.request.AddressInfoDto;
import com.t2m.g2nee.front.order.dto.request.BookOrderDto;
import com.t2m.g2nee.front.order.dto.request.CustomerOrderCheckRequestDto;
import com.t2m.g2nee.front.order.dto.request.OrdererInfoDto;
import com.t2m.g2nee.front.policyset.pointPolicy.dto.response.PointPolicyInfoDto;
import com.t2m.g2nee.front.policyset.pointPolicy.service.PointPolicyService;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private final MemberAspect memberAspect;
    private final BookGetService bookGetService;
    private final PointPolicyService pointPolicyService;
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
    @Member
    public String buyNowOrderForm(@RequestParam("bookId") Long bookId,@RequestParam("bookCount") Long bookCount, Model model){
        model.addAttribute("ordererInfo",new OrdererInfoDto());
        model.addAttribute("addressInfo",new AddressInfoDto());
        BigDecimal rewardRate = BigDecimal.valueOf(0);
        if(memberAspect.getThreadLocal().get()!=null){
            MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal().get();
            Long memberId = null;
            if(member!=null){
                memberId = member.getMemberId();
            }
            model.addAttribute("memberId",memberId);
            PointPolicyInfoDto pointPolicyInfoDto = pointPolicyService.getPointPolicyByPolicyName(member.getGrade());
            rewardRate=new BigDecimal(pointPolicyInfoDto.getAmount());
        }
        ArrayList<BookOrderDto> orderList= new ArrayList<>();
        BookDto.Response book = bookGetService.getBook(null, bookId);
        orderList.add(new BookOrderDto(
                book.getBookId(),
                book.getTitle(),
                bookCount,
                rewardRate,
                BigDecimal.valueOf(book.getSalePrice()).multiply(rewardRate).intValue(),
                book.getPrice(),
                book.getPrice()-book.getSalePrice(),
                0,
                book.getSalePrice()
                ));
        model.addAttribute("orderList",orderList);

        return "order/orderForm";
    }

}
