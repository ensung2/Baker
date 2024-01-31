package Baker.community.constant;

import Baker.community.provider.NaverUserInfo;
import Baker.community.provider.OAuth2UserInfo;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

@Getter
public enum OauthProvider {
    NAVER("naver", (attributes) -> {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return new NaverUserInfo(response);
    });

    private final String registrationId;
    private final Function<Map<String, Object>, OAuth2UserInfo> of;

    OauthProvider(String registrationId, Function<Map<String, Object>, OAuth2UserInfo> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static OAuth2UserInfo extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalAccessError::new)
                .of.apply(attributes);
    }
}
