package Baker.community.controller;

import Baker.community.domain.PrincipalDetails;
import Baker.community.dto.JoinMemberDto;
import Baker.community.entity.Member;
import Baker.community.repository.MemberRepository;
import Baker.community.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession httpSession;

    @GetMapping(value = "/login")
    public String loginFrom() {
        return "members/loginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요😥");
        return "members/loginForm";
    }

    @GetMapping(value = "/join")
    public String joinFrom(Model model) {
        model.addAttribute("joinMemberDto", new JoinMemberDto());
        return "members/joinForm";
    }


    @PostMapping(value = "/join")
    public String joinUser(@ModelAttribute @Valid JoinMemberDto joinMemberDto,
                           BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()){
            return "members/joinForm";
        }
        try {
            Member member = Member.createMember(joinMemberDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("joinErrorMsg", "이미 가입된 이메일입니다.");
            return "members/joinForm";
        }
        return  "members/loginForm";
    }

    // 마이페이지
    @GetMapping(value = "/myPage")
    public String myPage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        model.addAttribute("memberDto", principalDetails.getMember());
        return "members/myPage";
    }

    // 회원 삭제처리
    @GetMapping("/delete/{memberId}")
    public String delete(@PathVariable("memberId") Long memberId) throws Exception {
        memberService.deleteMember(memberId);
        return "redirect:/members/logout";
    }


}