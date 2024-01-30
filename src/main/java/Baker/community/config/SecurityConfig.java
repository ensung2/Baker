package Baker.community.config;

import Baker.community.constant.Role;
import Baker.community.service.MemberService;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    MemberService memberService;


    // 인증 관리자 관련 설정
    @Bean
    public static AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 특정 http 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        return  http
                // 1) csrf 공격 설정
                .csrf(csrf -> csrf
                        .csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers("/", "/login/**", "/logout/**", "/static/**")
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                )
                .headers(headers -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                )
                // 2) 로그인 시
                .formLogin(form -> form
                        .loginPage("/members/login")                            // 로그인 페이지 url
                        .loginProcessingUrl("/members/login")
                        .usernameParameter("email")                             // 로그인 시 사용할 파라미터 이름
                        .defaultSuccessUrl("/")                                 // 로그인 성공 시 이동할 url
                        .failureUrl("/members/login/error")  // 로그인 실패 시 이동할 url
                        .permitAll()
                )
                // 3) 로그아웃 시
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))     // 로그아웃 url
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/")                                                    // 로그아웃 성공 시 이동할 url
                        .permitAll()
                )
                .userDetailsService(memberService)
                // 4) 인증/인가
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/login", "/members/new","/youtube").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                        .requestMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                        .requestMatchers("/new_recipe", "/list/**").hasRole(Role.USER.name()) // 해당 경로는 인증된 사용자만 접근 가능
                        .anyRequest().authenticated()
                )
                // 5) 예외처리
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증 예외 핸들러 지정
                        .accessDeniedHandler(new CustomAccessDeniedHandler())    // 인가 예외 핸들러 지정
                )
                .httpBasic(Customizer.withDefaults())
//                .oauth2Login()
                .build();
    }

    // 패스워드 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}