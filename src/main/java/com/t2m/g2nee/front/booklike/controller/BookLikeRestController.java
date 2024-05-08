package com.t2m.g2nee.front.booklike.controller;

import static com.t2m.g2nee.front.aop.MemberAspect.MEMBER_INFO;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.booklike.dto.BookLikeDto;
import com.t2m.g2nee.front.booklike.service.BookLikeService;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * fetch용 like restcontroller 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class BookLikeRestController {

    private final BookLikeService bookLikeService;
    private final MemberAspect memberAspect;

    @Member
    @PutMapping
    public ResponseEntity<BookLikeDto> postLike(@RequestBody BookLikeDto request) {

        MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
        Long memberId = null;
        if(member!=null){
            memberId = member.getMemberId();
        }
        request.setMemberId(memberId);
        BookLikeDto bookLikeDto = bookLikeService.setLike(request);

        return ResponseEntity.ok().body(bookLikeDto);
    }

}
