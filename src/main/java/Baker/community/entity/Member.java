package Baker.community.entity;

import Baker.community.constant.Role;
import Baker.community.dto.JoinMemberDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
public class Member extends CreateModify /*implements UserDetails */{

    @Id
    @Column(unique = true, name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;        // 닉네임

    @Column(unique = true, nullable = false)
    private String email;       // 아이디

    private String password;    // 유저 비밀번호

    @Enumerated(EnumType.STRING)
    private Role role;

    private String provider;
    private String providerId;

    @Builder
    public Member(String username, String email, String password, Role role, String provider, String providerId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }


    public Member(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // member entity를 생성하는 메소드
    public static Member createMember(JoinMemberDto joinMemberDto,
                                      PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setUsername(joinMemberDto.getUsername());
        member.setEmail(joinMemberDto.getEmail());

        // 스프링 시큐리티 설정 클래스 빈을 파라미터로 넘김 -> 비밀번호 암호화
        String password = passwordEncoder.encode(joinMemberDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);
        return member;
    }

    public Member update(String username) {
        this.username = username;
        return this;
    }

}
