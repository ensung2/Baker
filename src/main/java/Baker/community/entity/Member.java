package Baker.community.entity;

import Baker.community.constant.Role;
import Baker.community.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends CreateModify implements UserDetails {

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

    @Builder
    public Member(String name, String email, String password,Role role) {
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

    @Override   // 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override   // 사용자 ID 반환(고유값)
    public String getUsername() {
        return email;
    }

    @Override   //사용자의 패스워드 반환
    public String getPassword() {
        return password;
    }

    @Override   // 계정 만료 여부 반환
    public boolean isAccountNonExpired() {
        return true;    // 만료되지 않음
    }

    @Override   // 계정 잠금 여부 반환
    public boolean isAccountNonLocked() {
        return true;    // 잠금되지 않음
    }

    @Override   // 패스워드 만료 여부 반환
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override   // 계정 사용 가능 여부 반환
    public boolean isEnabled() {
        return true;
    }
}
