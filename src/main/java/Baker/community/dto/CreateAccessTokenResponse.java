package Baker.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAccessTokenResponse { // 토큰 응답 담당
    private String accessToken;
}
