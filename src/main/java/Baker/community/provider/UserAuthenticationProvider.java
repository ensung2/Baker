package Baker.community.provider;

import Baker.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 1) 아이디 검증
        String email = authentication.getName();
        UserDetails userDetails = memberService.loadUserByUsername(email);

        String password = userDetails.getPassword();

        return new UsernamePasswordAuthenticationToken(email, password);
    }

    // 2) UsernamePasswordAuthenticationToken = Authentication 인터페이스를 구현한 인증 객체 (정보 담기)
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
