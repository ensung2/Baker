package Baker.community.provider;

import Baker.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
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

        // 2) 데이터베이스에서 가져온 비밀번호
        String password = userDetails.getPassword();

        // 3) 사용자가 입력한 비밀번호
        String providedPassword = authentication.getCredentials().toString();

        // 4) 비밀번호 검증
        if (!passwordEncoder.matches(providedPassword, password)) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
                    // 사용자 인증 토큰                // 매개변수        // 할당된 권한
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
    }

    // 2) UsernamePasswordAuthenticationToken = Authentication 인터페이스를 구현한 인증 객체 (정보 담기)
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
