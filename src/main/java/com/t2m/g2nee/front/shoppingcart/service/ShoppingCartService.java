package com.t2m.g2nee.front.shoppingcart.service;

import com.t2m.g2nee.front.shoppingcart.adaptor.ShoppingCartAdaptor;
import com.t2m.g2nee.front.shoppingcart.dto.ShoppingCartDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


/**
 * 장바구니 service 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartService {

    private final ShoppingCartAdaptor shoppingCartAdaptor;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * @param customerId          회원 아이디 또는 비회원의 세션아이디
     * @param request             장바구니 요청 객체
     * @param httpServletRequest  getBookForCart
     * @param httpServletResponse httpServletResponse
     * @return ShoppingCartDto.Response
     */
    public ShoppingCartDto.Response addBookInCart(String customerId, ShoppingCartDto.Request request,
                                                  HttpServletRequest httpServletRequest,
                                                  HttpServletResponse httpServletResponse) {

        customerId = getCustomerId(customerId, httpServletRequest, httpServletResponse);

        // 이미 장바구니에 있는 책이면 객체를 가져옵니다.
        ShoppingCartDto.Response cart =
                (ShoppingCartDto.Response) redisTemplate.opsForHash().get(customerId, request.getBookId());

        // 객체가 있다면 수량과 재고 값만 수정합니다.
        if (cart != null) {
            // 해당 객체를 가져올 때 책 수량과 담을 수량을 비교 후 담을 수량이 더 많다면 예외를 발생시킵니다.
            ShoppingCartDto.Response updateCart =
                    shoppingCartAdaptor.getBookForCart(request.getBookId(), request.getQuantity());
            cart.setQuantity(cart.getQuantity() + request.getQuantity());
            redisTemplate.opsForHash().put(customerId, cart.getBookId().toString(), cart);
            return updateCart;
        } else {
            // 객체가 없다면 해시 key(책아이디) value(장바구니객체) 값을 추가합니다.
            // shop 쪽에서 해당 객체를 가져올 때 책 수량과 담을 수량을 비교 후 담을 수량이 더 많다면 예외를 발생시킵니다.
            ShoppingCartDto.Response newCart =
                    shoppingCartAdaptor.getBookForCart(request.getBookId(), request.getQuantity());
            newCart.setCustomerId(customerId);
            redisTemplate.opsForHash().put(customerId, newCart.getBookId().toString(), newCart);
            return newCart;
        }
    }

    /**
     * 유저 아이디에 맞는 장바구니 책 정보를 가져오는 메서드
     *
     * @param customerId 회원 아이디 또는 비회원의 세션아이디
     * @return List<ShoppingCartDto.Response>
     */
    public List<ShoppingCartDto.Response> getCartByMember(String customerId,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {

        customerId = getCustomerId(customerId, httpServletRequest, httpServletResponse);

        Map<Object, Object> cartList = redisTemplate.opsForHash().entries(customerId);

        // 비회원일 경우 더미 데이터가 있기 때문에 제외하고 장바구니 객체를 조회
        if(cartList.get("expire") != null) {
            cartList.remove("expire");
        }

        List<ShoppingCartDto.Response> bookListInCart = new ArrayList<>();

        for (Object cart : cartList.values()) {
            bookListInCart.add((ShoppingCartDto.Response) cart);
        }
        return bookListInCart;
    }

    /**
     * 장바구니에서 책을 삭제하는 메서드
     *
     * @param customerId          회원 아이디 또는 비회원의 세션아이디
     * @param bookId              책 아이디
     * @param httpServletRequest
     * @param httpServletResponse
     * @return ShoppingCartDto.Response
     */
    public ShoppingCartDto.Response pullBookInCart(String customerId, String bookId,
                                                   HttpServletRequest httpServletRequest,
                                                   HttpServletResponse httpServletResponse) {

        // 비회원일 경우 memberId를 쿠키에서 세션id 값을 가져오거나 쿠키를 새로 생성하고 sessionId를 설정
        customerId = getCustomerId(customerId, httpServletRequest, httpServletResponse);
        ShoppingCartDto.Response cart =
                (ShoppingCartDto.Response) redisTemplate.opsForHash().get(customerId, bookId);
        redisTemplate.opsForHash().delete(customerId, bookId);
        return cart;

    }

    public ShoppingCartDto.Response updateCartQuantity(String customerId, ShoppingCartDto.Request request,
                                                       HttpServletRequest httpServletRequest,
                                                       HttpServletResponse httpServletResponse) {
        // 비회원일 경우 memberId를 쿠키에서 세션id 값을 가져오거나 쿠키를 새로 생성하고 sessionId를 설정
        customerId = getCustomerId(customerId, httpServletRequest, httpServletResponse);

        ShoppingCartDto.Response cart =
                (ShoppingCartDto.Response) redisTemplate.opsForHash().get(customerId, request.getBookId());

        Objects.requireNonNull(cart).setQuantity(request.getQuantity());
        redisTemplate.opsForHash().put(customerId, cart.getBookId().toString(), cart);
        return cart;

    }

    /**
     * @param memberId           회원 아이디
     * @param httpServletRequest httpServletRequest
     * @return int
     */
    public int getCartItemNum(Long memberId, HttpServletRequest httpServletRequest) {

        // 회원 아이디가 있으면 회원 아이디로 찾기
        if (memberId != null) {
            return redisTemplate.opsForHash().entries(memberId.toString()).size();
            // 회원 아이디가 없으면 비회원으로 찾기
        } else {

            Cookie[] cookies = httpServletRequest.getCookies();
            for (Cookie c : cookies) {
                if (c.getName().equals("cart")) {
                    // 비회원은 유효기간 확인용 더미 더미데이터가 존재하여 개수 1를 뺴줍니다.
                    return redisTemplate.opsForHash().entries(c.getValue()).size() - 1;
                }
            }
        }
        return 0;
    }

    /**
     * 고객의 아이디를 얻는 메서드
     *
     * @param customerId          회원 또는 비회원 세션 아이디
     * @param httpServletRequest httpServletRequest
     * @param httpServletResponse httpServletResponse
     * @return String
     */
    private String getCustomerId(String customerId, HttpServletRequest httpServletRequest,
                                 HttpServletResponse httpServletResponse) {

        // 비회원일 경우 memberId를 쿠키에서 세션id 값을 가져오거나 쿠키를 새로 생성하고 sessionId를 설정
        if (customerId == null) {
            customerId = getCartSessionId(httpServletRequest);
            generateCart(customerId);

            if (!StringUtils.hasText(customerId)) {
                customerId = createCartCookie(httpServletRequest, httpServletResponse);
            }
        }
        return customerId;
    }

    /**
     * 비회원 장바구니 생성 // 유효기간 설정을 위함
     * @param customerId 회원 또는 비회원 세션 아이디
     */
    private void generateCart(String customerId) {
        // 비회원일 경우 장바구니가 없으면 생성 후 장바구니에 한달 유효기간을 설정합니다.
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(customerId);
        if (entries.isEmpty()) {
            redisTemplate.opsForHash().put(customerId, "expire", LocalDateTime.now().plusDays(30).toString());
            redisTemplate.expire(customerId, 30, TimeUnit.DAYS);
        }
    }

    /**
     * 비회원일 경우 ( memberId == null ) 쿠키에서 세션 아이디를 얻는 메서드
     *
     * @param httpRequest httpRequest
     * @return String
     */
    private String getCartSessionId(HttpServletRequest httpRequest) {
        Cookie[] cookies = httpRequest.getCookies();

        for (Cookie c : cookies) {
            if (c.getName().equals("cart")) {
                return c.getValue();
            }
        }
        return null;
    }

    /**
     * 세션 아이디 쿠키 생성 메서드
     *
     * @param httpRequest httpRequest
     * @param httpResponse httpResponse
     * @return String
     */
    private String createCartCookie(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        String sessionId = httpRequest.getSession().getId();
        Cookie cookie = new Cookie("cart", sessionId);
        httpResponse.addCookie(cookie);
        return sessionId;
    }
}
