package Baker.community.domain;

import Baker.community.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {

    private Member member;
    private Map<String, Object> attributes;

    // 일반 로그인 용
    public PrincipalDetails(Member member) {
        this.member = member;
    }

    // OAuth 로그인 용
    public PrincipalDetails(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return member.getName();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 해당 member의 권한을 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> member.getRole().name());
        return collection;

    }

    @Override   // 사용자 ID 반환(고유값)
    public String getUsername() {
        return member.getName();
    }

    @Override   //사용자의 패스워드 반환
    public String getPassword() {
        return member.getPassword();
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
