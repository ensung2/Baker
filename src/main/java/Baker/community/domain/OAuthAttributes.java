//package Baker.community.domain;
//
//import Baker.community.constant.Role;
//import Baker.community.entity.Member;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.Map;
//
//@Getter
//public class OAuthAttributes {
//    private Map<String, Object> attributes;
//    private String nameAttributeKey;
//    private String name;
//    private String email;
//
//    @Builder
//    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
//        this.attributes = attributes;
//        this.nameAttributeKey = nameAttributeKey;
//        this.name = name;
//        this.email = email;
//    }
//
//    public static OAuthAttributes of(String registrationId,
//                                     String userNameAttributeName,
//                                     Map<String, Object> attributes) {
//
//        OAuthAttributes result = null;
//
//        if("naver".equals(registrationId)) {
//            result = ofNaver("id", attributes);
//        }
//
//        return result;
//    }
//
//    @SuppressWarnings("unchecked")
//    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
//
//        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
//
//        return OAuthAttributes.builder()
//                .name((String) response.get("name"))
//                .email((String) response.get("email"))
//                .attributes(response)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//
//    public Member toEntity() {
//        return Member.builder()
//                .name(name)
//                .email(email)
//                .role(Role.USER)
//                .build();
//    }
//
//}
