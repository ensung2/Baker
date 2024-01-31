package Baker.community.service;

import Baker.community.constant.OauthProvider;
import Baker.community.constant.Role;
import Baker.community.domain.PrincipalDetails;
import Baker.community.entity.Member;
import Baker.community.provider.OAuth2UserInfo;
import Baker.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverOAuthUserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

/*    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        return user;
    }*/

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("[MemberService AccessToken] : {}", userRequest.getAccessToken().getTokenValue());
        log.info("[MemberService getClientRegistration] : {}", userRequest.getClientRegistration());
        log.info("[MemberService getAttributes] : {}", oAuth2User.getAttributes());

        OAuth2UserInfo userInfo = OauthProvider.extract(userRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes());

        String provider = userInfo.getProvider();
        String providerId = userInfo.getProviderId();
        String name = provider + "_" + providerId;
        String email = userInfo.getEmail();

        Member member = memberRepository.findByEmail(email).orElseGet(()->{
            Member newMember = Member.builder()
                    .provider(provider)
                    .providerId(providerId)
                    .name(name)
                    .email(email)
                    .role(Role.USER)
                    .build();
            return memberRepository.save(newMember);
        });

    return new PrincipalDetails(member, oAuth2User.getAttributes());
    }



/*    private Member saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attribute = oAuth2User.getAttributes();
        String email = (String) attribute.get("email");
        String name = (String) attribute.get("name");
        Member member = memberRepository.findByEmail(email)
                .map(entity -> entity.update(name))
                .orElse(Member.builder()
                        .email(email)
                        .name(name)
                        .build());
        return memberRepository.save(member);
    }*/
}

