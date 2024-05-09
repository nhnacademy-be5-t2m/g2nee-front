package com.t2m.g2nee.front.shoppingcart.controller;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.booklike.service.BookLikeService;
import com.t2m.g2nee.front.policyset.deliveryPolicy.dto.response.DeliveryPolicyInfoDto;
import com.t2m.g2nee.front.policyset.deliveryPolicy.service.DeliveryPolicyService;
import com.t2m.g2nee.front.shoppingcart.dto.ShoppingCartDto;
import com.t2m.g2nee.front.shoppingcart.service.ShoppingCartService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 장바구니 controller 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final MemberAspect memberAspect;
    private final BookLikeService bookLikeService;
    private final DeliveryPolicyService deliveryPolicyService;

    /**
     * 레디스에서 고객의 장바구니를 가져오는 컨트롤러
     * @param model
     * @return
     */
    @Member
    @GetMapping("/customer")
    public String getCartByMember(Model model, HttpServletRequest httpServletRequest,
                                  HttpServletResponse httpServletResponse) {

        String customerId = null;
        Long memberId = (Long) memberAspect.getThreadLocal().get();
        if(memberId != null) customerId = memberId.toString();
        Long likesNum = bookLikeService.getMemberLikesNum(memberId);
        List<ShoppingCartDto.Response> cartList = shoppingCartService.getCartByMember(customerId,httpServletRequest,httpServletResponse);

        int totalPrice = cartList.stream()
                .mapToInt(c -> c.getPrice() * c.getQuantity())
                .sum();

        DeliveryPolicyInfoDto deliveryPolicy = deliveryPolicyService.getDeliveryPolicy();

        model.addAttribute("cartList", cartList);
        model.addAttribute("likesNum", likesNum);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("deliveryPolicy", deliveryPolicy);

        return "main/cart";
    }
}
