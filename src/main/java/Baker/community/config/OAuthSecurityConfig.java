package Baker.community.config;//package Baker.community.config;

import Baker.community.config.jwt.TokenProvider;
import Baker.community.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import Baker.community.config.oauth.OAuth2SuccessHandler;
import Baker.community.constant.Role;
import Baker.community.filter.TokenAuthenticationFilter;
import Baker.community.repository.RefreshTokenRepository;
import Baker.community.service.MemberService;
import Baker.community.service.NaverOAuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class OAuthSecurityConfig {

    private final NaverOAuthUserService naverOAuthUserService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberService memberService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/img/**", "/css/**", "/js/**");
    }

    // 특정 http 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);
//        http.sessionManagement(sessionManagement -> sessionManagement
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/api/token").permitAll()
                .requestMatchers("/new_recipe/**", "/list/**").hasRole(Role.USER.name()) // 해당 경로는 인증된 사용자만 접근 가능
                .anyRequest().permitAll());
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
                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                        .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository()))
                .successHandler(oAuth2SuccessHandler())
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                        .userService(naverOAuthUserService)));
        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))     // 로그아웃 url
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/"));
        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher("/api/**")));


        return http.build();
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                memberService
        );
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    // 패스워드 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}