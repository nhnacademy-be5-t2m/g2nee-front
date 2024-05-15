package com.t2m.g2nee.front.mypage.member.conrtoller;

import static com.t2m.g2nee.front.aop.MemberAspect.MEMBER_INFO;

import com.t2m.g2nee.front.annotation.Member;
import com.t2m.g2nee.front.aop.MemberAspect;
import com.t2m.g2nee.front.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.front.member.service.MemberService;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MemberService memberService;
    private final MemberAspect memberAspect;

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

    /**
     * 회원 등급을 보여주는 페이지
     *
     * @return 마이페이지의 회원페이지
     */
    @GetMapping("/grade")
    @Member
    public String getGrade(Model model) {
        MemberDetailInfoResponseDto member = (MemberDetailInfoResponseDto) memberAspect.getThreadLocal(MEMBER_INFO);
        Long memberId = null;
        if (member != null) {
            memberId = member.getMemberId();
        }
        GradeResponseDto gradeInfo= memberService.changeGrade(memberId);
        model.addAttribute("gradeInfo",gradeInfo);
        return "mypage/gradePage";
    }
}
