package Baker.community.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigTest {  // 비밀번호 encoding  하는법

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("비밀번호 암호화 테스트")
    void pwdEnc() {
    String pwd = "123456789asd";
    String encodedPwd = passwordEncoder.encode(pwd);
    System.out.println(encodedPwd);
    }

    @Test
    @DisplayName("비밀번호 확인 테스트")
    void pwdMatch(){
        String encodidPwd = "$2a$10$ZfXGehUbYew3JNrxC6VoyeKGVWCg3xfEDItViMTQcjiAFQViu49dm";
        String pwd = "123456789asd";

        if (passwordEncoder.matches(pwd, encodidPwd)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}