package Baker.community.config;

import Baker.community.service.MemberService;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    MemberService memberService;

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        return  http
                // 1) csrf 공격 설정
                .csrf(csrf -> csrf
                        .csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers("/", "/account/login/**", "/logout/**", "/register/validate/email")
                )
                // 2) 로그인 시
                .formLogin(form -> form
                        .loginPage("/members/login")                            // 로그인 페이지 url
                        .loginProcessingUrl("/members/login")
                        .usernameParameter("email")                             // 로그인 시 사용할 파라미터 이름
                        .defaultSuccessUrl("/")                                 // 로그인 성공 시 이동할 url
                        .failureUrl("/members/login/error")  // 로그인 실패 시 이동할 url
                )
                // 3) 로그아웃 시
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))     // 로그아웃 url
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/")                                                    // 로그아웃 성공 시 이동할 url
                )
                // 4) 인증/인가
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/list", "/recipe/write").hasRole("USER")
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                        .requestMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                // 5) 예외처리
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    // 패스워드 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}