package Baker.community.service;

import Baker.community.constant.OAuthProvider;
import Baker.community.constant.Role;
import Baker.community.domain.PrincipalDetails;
import Baker.community.entity.Member;
import Baker.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import Baker.community.provider.OAuth2UserInfo;

@Service
@RequiredArgsConstructor
public class NaverOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo userInfo = OAuthProvider.extract(userRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes());

        // 회원가입
        String provider = userInfo.getProvider();
        String providerId = userInfo.getProviderId();
        String username = userInfo.getName();
        String email = userInfo.getEmail();

        Member member = memberRepository.findByEmail(email).orElseGet(()-> {
            Member newMember = Member.builder()
                    .provider(provider)
                    .providerId(providerId)
                    .username(username)
                    .email(email)
                    .role(Role.USER)
                    .build();
            return memberRepository.save(newMember);
        });
       return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
