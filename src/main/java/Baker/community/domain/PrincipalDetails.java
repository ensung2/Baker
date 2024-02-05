package Baker.community.domain;

import Baker.community.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {

    private Member member;
    private Map<String, Object> attributes;

    // 일반 로그인용
    public PrincipalDetails(Member member){
        this.member = member;
    }

    // OAuth 로그인 용
    @Builder
    public PrincipalDetails(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }

    // 해당 member의 권한을 반환
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collection = new ArrayList<>();
//        collection.add((GrantedAuthority) () -> member.getRole().name());
//        return collection;
//    }



    @Override // Granted Authorities=[ROLE_USER] DEBUG 확인 가능 (24.02.02)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // member.getRole().name()이 권한을 나타내는 문자열이라고 가정합니다.
        String roleString = member.getRole().name();

        // "ROLE_"을 접두사로 붙여서 권한을 나타내는 문자열로 변환
        String authorityString = "ROLE_" + roleString;

        // 권한을 문자열로 변환하여 GrantedAuthority 객체로 추가
        authorities.add(new SimpleGrantedAuthority(authorityString));

        return authorities;
    }


    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
