package com.t2m.g2nee.front.order.controller;


import static com.t2m.g2nee.front.aop.MemberAspect.MEMBER_INFO;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.bookset.book.service.BookGetService;
import com.t2m.g2nee.front.member.dto.request.SignUpNonMemberRequestDto;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.member.service.MemberService;
import com.t2m.g2nee.front.order.dto.request.BookOrderDto;
import com.t2m.g2nee.front.order.dto.request.CustomerOrderCheckRequestDto;
import com.t2m.g2nee.front.order.dto.request.OrderForm;
import com.t2m.g2nee.front.order.dto.request.OrderSaveRequestDto;
import com.t2m.g2nee.front.order.dto.response.OrderForPaymentDto;
import com.t2m.g2nee.front.order.service.OrderService;
import com.t2m.g2nee.front.orderset.packagetype.service.PackageTypeService;
import com.t2m.g2nee.front.point.service.PointService;
import com.t2m.g2nee.front.policyset.deliverypolicy.dto.response.DeliveryPolicyInfoDto;
import com.t2m.g2nee.front.policyset.deliverypolicy.service.DeliveryPolicyService;
import com.t2m.g2nee.front.policyset.pointpolicy.dto.response.PointPolicyInfoDto;
import com.t2m.g2nee.front.policyset.pointpolicy.service.PointPolicyService;
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
    private final DeliveryPolicyService deliveryPolicyService;
    private final PointService pointService;
    private final PackageTypeService packageTypeService;
    private final MemberService memberService;
    private final OrderService orderService;

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
     * @param bookId    구매할 책의 id
     * @param bookCount 구매할 책의 수량
     * @param model     주문 form 에 필요한 dto 객체를 저장할 model
     * @return 주문페이지를 보여준다.
     */
    @GetMapping("/buyNow")
    @Member
    public String buyNowOrderForm(@RequestParam("bookId") Long bookId, @RequestParam("bookCount") int bookCount,
                                  Model model) {
        BigDecimal rewardRate = BigDecimal.valueOf(0);

        MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
        //회원주문인지 비회원주문인지 확인
        //회원이라면 그 회원의 적립률을 저장한다.
        if (member != null) {
            Long memberId = null;
            if (member != null) {
                memberId = member.getMemberId();
            }
            model.addAttribute("memberId", memberId);
            PointPolicyInfoDto pointPolicyInfoDto = pointPolicyService.getPointPolicyByPolicyName(member.getGrade());
            rewardRate = new BigDecimal(pointPolicyInfoDto.getAmount());

            //회원의 포인트 합계 저장
            Integer totalPoint = pointService.getTotalPoint(memberId);
            model.addAttribute("totalPoint", totalPoint == null ? 0 : totalPoint);
        }
        //무료배송 기준 저장
        DeliveryPolicyInfoDto deliveryInfo = deliveryPolicyService.getDeliveryPolicy();
        model.addAttribute("freeDeliveryStandard", deliveryInfo.getFreeDeliveryStandard());
        model.addAttribute("deliveryFeePolicy", deliveryInfo.getDeliveryFee());


        //주문할 책의 정보
        ArrayList<BookOrderDto> orderList = new ArrayList<>();
        BookDto.Response book = bookGetService.getBook(null, bookId);
        BookOrderDto bookOrderDto = new BookOrderDto(
                book.getBookId(),
                book.getTitle(),
                bookCount,
                1L,
                0,
                rewardRate,
                BigDecimal.valueOf(book.getSalePrice()).multiply(rewardRate).multiply(BigDecimal.valueOf(bookCount))
                        .intValue(),
                book.getPrice() * bookCount,
                (book.getPrice() - book.getSalePrice()) * bookCount,
                0,
                book.getSalePrice() * bookCount
        );
        orderList.add(bookOrderDto);
        model.addAttribute("finalTotalOriginPrice", bookOrderDto.getOriginPrice());
        model.addAttribute("totalBookSale", bookOrderDto.getBookSale());
        model.addAttribute("totalPointSale", 0);
        model.addAttribute("totalCouponSale", bookOrderDto.getCouponSale());
        model.addAttribute("orderList", orderList);
        model.addAttribute("totalPackagePrice", 0);
        int deliveryFee;
        if (bookOrderDto.getFinalPrice() >= deliveryInfo.getFreeDeliveryStandard()) {
            deliveryFee = 0;
        } else {
            deliveryFee = deliveryInfo.getDeliveryFee();
        }
        model.addAttribute("deliveryFee", deliveryFee);
        model.addAttribute("finalTotalSalePrice", bookOrderDto.getFinalPrice() + deliveryFee);
        return "order/orderForm";
    }

    @Member
    @PostMapping("/payment")
    public String submitOrder(@ModelAttribute("form") OrderForm request) {
        MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
        //회원주문인지 비회원주문인지 확인
        //비회원이라면 비회원을 생성한 후 customerId저장
        if (member != null) {
            Long memberId = null;
            if (member != null) {
                request.setCustomerId(memberId);
            }
        } else {
            SignUpNonMemberRequestDto signUpNonMemberRequestDto
                    = new SignUpNonMemberRequestDto(
                    request.getName(),
                    request.getPassword(),
                    request.getEmail(),
                    request.getPhoneNumber()
            );
            request.setCustomerId(memberService.nonMemberSignUp(signUpNonMemberRequestDto));
        }

        //주문 생성
        OrderSaveRequestDto order = new OrderSaveRequestDto(
                request.getDeliveryWishDate(),
                request.getNetAmount(),
                request.getOrderAmount(),
                request.getDeliveryFee(),
                request.getReceiverName(),
                request.getReceiverPhoneNumber(),
                request.getReceiverAddress(),
                request.getZipcode(),
                request.getDetailAddress(),
                request.getMessage(),
                request.getOrderDetailList(),
                request.getCustomerId(),
                request.getCouponId()
        );
        OrderForPaymentDto orderForPaymentDto = orderService.saveOrder(order);

        Long point = request.getPoint();
        return "/"; //TODO : 결제페이지로
    }


    /**
     * 포장지 선택 창을 띄워주는 메소드
     *
     * @param
     * @return 포장지 선택 페이지
     */
    @GetMapping("/selectPackage/{id}")
    public String selectPackage(@RequestParam(defaultValue = "1") int page, @PathVariable("id") String id,
                                Model model) {
        model.addAttribute("packages", packageTypeService.getActivatedPackage(page));
        model.addAttribute("id", id);
        return "order/selectPackagePage";
    }
}
