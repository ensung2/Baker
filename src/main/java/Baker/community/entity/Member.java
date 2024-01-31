package Baker.community.entity;

import Baker.community.constant.Role;
import Baker.community.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends CreateModify{

    @Id
    @Column(unique = true, name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // 닉네임

    @Column(unique = true, nullable = false)
    private String email;       // 아이디

    private String password;    // 유저 비밀번호

    @Enumerated(EnumType.STRING)
    private Role role;

    private String provider;
    private String providerId;

    @Builder
    public Member(String name, String email, String password, Role role, String provider, String providerId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }


    public Member(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // member entity를 생성하는 메소드
    public static Member createMember(MemberFormDto memberFormDto,
                                      PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setRole(Role.USER);

        // 스프링 시큐리티 설정 클래스 빈을 파라미터로 넘김 -> 비밀번호 암호화
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);

        return member;
    }

    public Member update(String name) {
        this.name = name;
        return this;
    }


}
