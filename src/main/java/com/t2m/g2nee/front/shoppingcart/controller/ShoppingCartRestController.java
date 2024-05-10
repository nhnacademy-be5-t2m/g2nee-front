package com.t2m.g2nee.front.shoppingcart.controller;

import static com.t2m.g2nee.front.aop.MemberAspect.MEMBER_INFO;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.shoppingcart.dto.ShoppingCartDto;
import com.t2m.g2nee.front.shoppingcart.service.ShoppingCartService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 장바구니 controller 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class ShoppingCartRestController {

    private final ShoppingCartService shoppingCartService;
    private final MemberAspect memberAspect;

    /**
     * 레디스 DB에 장바구니 객체를 추가하는 컨트롤러
     * @param request 회원아이디, 책아이디, 수량 이 담긴 객체
     * @param httpServletRequest
     * @param httpServletResponse
     * @return ResponseEntity<ShoppingCartDto.Response>
     */
    @Member
    @PostMapping
    public ResponseEntity<ShoppingCartDto.Response> addBookInCart(@RequestBody ShoppingCartDto.Request request,
                                                                  HttpServletRequest httpServletRequest,
                                                                  HttpServletResponse httpServletResponse) {


        String customerId = null;
        MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
        if (member != null) {
            customerId = member.getMemberId().toString();
        }

        ShoppingCartDto.Response response =
                shoppingCartService.addBookInCart(customerId, request, httpServletRequest, httpServletResponse);

        return ResponseEntity.ok(response);
    }
    /**
     * 레디스 DB에 장바구니 객체를 삭제하는 컨트롤러
     * @param bookId 책아이디
     * @param httpServletRequest
     * @param httpServletResponse
     * @return ResponseEntity<ShoppingCartDto.Response>
     */
    @Member
    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<ShoppingCartDto.Response> pullBookInCart(@PathVariable String bookId,
                                                                   HttpServletRequest httpServletRequest,
                                                                   HttpServletResponse httpServletResponse

                                                                   ) {
        String customerId = null;
        MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
        if (member != null) {
            customerId = member.getMemberId().toString();
        }

        ShoppingCartDto.Response response =
                shoppingCartService.pullBookInCart(customerId, bookId, httpServletRequest,
                        httpServletResponse);

        return ResponseEntity.ok(response);

    }

    /**
     * 책 수량을 변경하는 컨트롤러
     * @param request 책아이디와 수량이 담긴 객체
     * @param httpServletRequest
     * @param httpServletResponse
     * @return ResponseEntity<ShoppingCartDto.Response>
     *
     */
    @Member
    @PatchMapping
    public ResponseEntity<ShoppingCartDto.Response> updateCartQuantity(@RequestBody ShoppingCartDto.Request request,
                                                                        HttpServletRequest httpServletRequest,
                                                                        HttpServletResponse httpServletResponse){
        String customerId = null;
        MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
        if (member != null) {
            customerId = member.getMemberId().toString();
        }

        ShoppingCartDto.Response response =
                shoppingCartService.updateCartQuantity(customerId, request, httpServletRequest, httpServletResponse);

        return ResponseEntity.ok(response);
    }

}
