package Baker.community.entity;

import Baker.community.constant.Role;
import Baker.community.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;            // 유저 번호

    private String name;        // 닉네임

    @Column(name = "member_email")
    private String email;       // 아이디

    private String password;    // 유저 비밀번호

    @Enumerated(EnumType.STRING)
    private Role role;

    // member entity를 생성하는 메소드
    public static Member createMember(MemberFormDto memberFormDto,
                                      PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setPassword(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());

        // 스프링 시큐리티 설정 클래스 빈을 파라미터로 넘김 -> 비밀번호 암호화
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);
        return member;
    }
}
