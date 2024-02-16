package Baker.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RevokeService {
//
//    private final MemberRepository memberRepository;
//    private final MemberService memberService;
//    private final NaverOAuth2UserService naverOAuth2UserService;
//
//    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
//    private String naverClientId;
//
//    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
//    private String naverClientSecret;
//
//    public void deleteNaverAccount(String accessToken) {
//        Member member = extractUserFromAccessToken(accessToken);
//        deleteUserAccount(member);
//
//        String data = "client_id=" + naverClientId +
//                "&client_secret=" + naverClientSecret +
//                "&access_token=" + member.getProviderId() +
//                "&service_provider=NAVER" +
//                "&grant_type=delete";
//
//        sendRevokeRequest(data, AuthProvider.NAVER, null);
//    }
//
//    private Member extractUserFromAccessToken(String accessToken) {
//        Optional<String> email = naverOAuth2UserService.extractClaimFromJWT(naverOAuth2UserService.CLAIM_EMAIL, extractToken(accessToken));
//        if (email.isEmpty()) {
//            throw new BadRequestException(ResponseCode.USER_NOT_FOUND);
//        }
//
//        Optional<Member> userAccount = memberRepository.findByEmail(email.get());
//        if (userAccount.isEmpty()) {
//            throw new BadRequestException(ResponseCode.USER_NOT_FOUND);
//        }
//
//        return mem.get();
//    }
//
//    private void deleteUserAccount(NaverUserInfo naverUserInfo) {
//        // 유저 관련 데이터 DB에서 삭제
//        memberRepository.delete(naverUserInfo);
//    }
//
//    private void sendRevokeRequest(String data, AuthProvider provider, String accessToken) {
//        String naverRevokeUrl = "https://nid.naver.com/oauth2.0/token";
//
//        RestTemplate restTemplate = new RestTemplate();
//        String revokeUrl = "";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        HttpEntity<String> entity = new HttpEntity<>(data, headers);
//
//        if (provider.equals(NAVER)) {
//            revokeUrl = naverRevokeUrl;
//        }
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(revokeUrl, HttpMethod.POST, entity, String.class);
//
//    }
}
