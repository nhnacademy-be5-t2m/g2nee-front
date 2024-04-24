package com.t2m.g2nee.front.booklike.controller;

import static com.t2m.g2nee.front.token.util.JwtUtil.ACCESS_COOKIE;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.booklike.dto.BookLikeDto;
import com.t2m.g2nee.front.booklike.service.BookLikeService;
import com.t2m.g2nee.front.token.util.JwtUtil;
import com.t2m.g2nee.front.utils.CookieUtil;
import javax.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/likes")
@RequiredArgsConstructor
public class BookLikeController {

    private final BookLikeService bookLikeService;
    private MemberAspect memberAspect;

    @PostMapping
    @Member
    public void postLike(@ModelAttribute BookLikeDto request){

        Cookie cookie = CookieUtil.findCookie(ACCESS_COOKIE);
        String accessToken = cookie.getValue();
        Long memberId = JwtUtil.getMemberId(accessToken);



        request.setMemberId(memberId);
        bookLikeService.setLike(request);
    }

}
