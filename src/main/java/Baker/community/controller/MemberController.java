package Baker.community.controller;

import Baker.community.dto.MemberFormDto;
import Baker.community.entity.Member;
import Baker.community.service.MemberService;
import Baker.community.service.NaverOAuthUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final NaverOAuthUserService naverOAuthUserService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "members/memberForm";
    }

    @PostMapping("/new")
    public String newMember(@ModelAttribute @Valid MemberFormDto memberFormDto,
                            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()){
            return "members/memberForm";
        }
        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "members/memberForm";
        }
        return  "members/loginForm";
    }

    @GetMapping(value = "/login")
    public String loginForm() {
        return "members/loginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요😥");
        return "members/loginForm";
    }

    @GetMapping(value = "/oauth2/authorization/naver")
    public String naverLogin() {
        return "main";
    }


}