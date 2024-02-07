package Baker.community.config;

import Baker.community.constant.Role;
import Baker.community.service.MemberService;
import Baker.community.service.NaverOAuth2UserService;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class OAuthSecurityConfig {

    private final MemberService memberService;
    private final NaverOAuth2UserService naverOAuth2UserService;

    public static String[] ONLY_USER = {
            "/recipe/**",
            "/list/**",
            "/members/myPage"
    };

    // 패스워드 암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 특정 http 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable);
        http.csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
        http.headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.httpBasic(Customizer.withDefaults());
//        http.httpBasic(AbstractHttpConfigurer::disable);
//        http.sessionManagement(sessionManagement -> sessionManagement
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
        http.authorizeHttpRequests(request -> request
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers("static/**", "/").permitAll()
                .requestMatchers(ONLY_USER).hasRole(Role.USER.name()) // 해당 경로는 인증된 사용자만 접근 가능
                .anyRequest().permitAll());
        http.userDetailsService(memberService);
        http.formLogin(form -> form
                        .loginPage("/members/login")                            // 로그인 페이지 url
                        .loginProcessingUrl("/members/login")
                        .usernameParameter("email")                             // 로그인 시 사용할 파라미터 이름
                        .defaultSuccessUrl("/")                                 // 로그인 성공 시 이동할 url
                        .failureUrl("/members/login/error")  // 로그인 실패 시 이동할 url
                        .permitAll()
                );
        http.oauth2Login(oauth2Login -> oauth2Login
                .loginPage("/login")
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                        .userService(naverOAuth2UserService)));
        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))     // 로그아웃 url
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/"));
        http.exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증 예외 핸들러 지정
                .accessDeniedHandler(new CustomAccessDeniedHandler())    // 인가 예외 핸들러 지정
        );

        return http.build();
    }
}