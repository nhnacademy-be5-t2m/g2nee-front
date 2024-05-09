package com.t2m.g2nee.front.shoppingcart.controller;

import static com.t2m.g2nee.front.aop.MemberAspect.CART_ITEM_NUM;
import static com.t2m.g2nee.front.aop.MemberAspect.LIKE_NUM;
import static com.t2m.g2nee.front.aop.MemberAspect.MEMBER_INFO;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.booklike.service.BookLikeService;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.policyset.deliverypolicy.dto.response.DeliveryPolicyInfoDto;
import com.t2m.g2nee.front.policyset.deliverypolicy.service.DeliveryPolicyService;
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
        MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
        if (member != null) {
            customerId = member.getMemberId().toString();
        }
        Long likesNum = (Long) memberAspect.getThreadLocal(LIKE_NUM);
        int cartItemNum = (int) memberAspect.getThreadLocal(CART_ITEM_NUM);
        List<ShoppingCartDto.Response> cartList = shoppingCartService.getCartByMember(customerId,httpServletRequest,httpServletResponse);

        int totalPrice = cartList.stream()
                .mapToInt(c -> c.getPrice() * c.getQuantity())
                .sum();

        DeliveryPolicyInfoDto deliveryPolicy = deliveryPolicyService.getDeliveryPolicy();

        model.addAttribute("cartList", cartList);
        model.addAttribute("likesNum", likesNum);
        model.addAttribute("cartItemNum", cartItemNum);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("deliveryPolicy", deliveryPolicy);

        return "main/cart";
    }
}
