package com.t2m.g2nee.front.mypage.member.conrtoller;

import static com.t2m.g2nee.front.aop.MemberAspect.CART_ITEM_NUM;
import static com.t2m.g2nee.front.aop.MemberAspect.LIKE_NUM;
import static com.t2m.g2nee.front.aop.MemberAspect.MEMBER_INFO;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.member.service.MemberService;
import com.t2m.g2nee.front.point.dto.PointResponseDto;
import com.t2m.g2nee.front.point.service.PointService;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MemberService memberService;
    private final MemberAspect memberAspect;
    private final PointService pointService;


    /**
     * 회원 탈퇴 후 메인으로 이동하는 메소드
     *
     * @return 메인페이지
     */
    @GetMapping("/quitMember")
    @Member
    public String quitMember(HttpServletResponse response) {
        MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
        Long memberId = null;
        if (member != null) {
            memberId = member.getMemberId();
        }
        memberService.quitMember(memberId);
        memberService.logout(response);
        return "redirect:/";
    }


    @Member
    @GetMapping("/pointdetail/member")
    public String getMemberPointDetail(Model model){

        MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
        Long memberId = null;
        if (member != null) {
            memberId = member.getMemberId();
        }

        PageResponse<PointResponseDto> pointPage = pointService.getMemberPointDetail(memberId);
        Long likesNum = (Long) memberAspect.getThreadLocal(LIKE_NUM);
        int cartItemNum = (int) memberAspect.getThreadLocal(CART_ITEM_NUM);

        model.addAttribute("pointPage",pointPage);
        model.addAttribute("likesNum",likesNum);
        model.addAttribute("cartItemNum",cartItemNum);
        model.addAttribute("memberId",memberId);

        return "mypage/pointPage";
    }

}
