package Baker.community.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

public class CookieUtil {

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletResponse response, HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }


//    public static <T> void addSerializedCookie(HttpServletResponse response, String name, T obj, int maxAge) {
//        String serializedValue = serialize(obj);
//        addCookie(response, name, serializedValue, maxAge);
//    }
//

//    public static <T> T getDeserializedCookie(HttpServletRequest request, String name, Class<T> cls) {
//        Cookie cookie = getCookie(request, name);
//        return (cookie != null) ? deserialize(cookie, cls) : null;
//    }

//    private static Cookie getCookie(HttpServletRequest request, String name) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (name.equals(cookie.getName())) {
//                    return cookie;
//                }
//            }
//        }
//        return null;
//    }

    // 객체를 직렬화해서 쿠키 값으로 변환
    public static String serialize(Object obj) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(obj));
    }
    // 쿠키에서 값을 역직렬화해서 객체로 변환
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())
                )
        );
    }
}

