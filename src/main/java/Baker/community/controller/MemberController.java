package Baker.community.controller;

import Baker.community.dto.JoinMemberDto;
import Baker.community.entity.Member;
import Baker.community.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginFrom() {
        return "members/loginForm";
    }

    @GetMapping("/join")
    public String joinFrom(Model model) {
        model.addAttribute("joinMemberDto", new JoinMemberDto());
        return "members/joinForm";
    }


    @PostMapping("/join")
    public String joinUser(@ModelAttribute @Valid JoinMemberDto joinMemberDto,
                           BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()){
            return "members/joinForm";
        }
        try {
            Member member = Member.createMember(joinMemberDto, passwordEncoder);
            memberService.saveMember(member);
//            memberService.joinUser(joinMemberDto.getUsername(),
//                    joinMemberDto.getEmail(), joinMemberDto.getPassword());
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "members/joinForm";
        }
        return  "members/loginForm";
    }


}