package Baker.community.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 인증되어 있고, 사용자 권한이 ROLE_USER인 경우
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserDetails) {
            UserDetails user = (UserDetails) auth.getPrincipal();
            if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
                // 사용자가 ROLE_USER 권한을 가지고 있으면 요청 경로로 리다이렉트
                String requestedPath = getRequestedPath(request);
                response.sendRedirect(requestedPath);
                return;
            }
        }

        // 그 외의 경우에는 403 Forbidden 응답을 반환
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }

    private String getRequestedPath(HttpServletRequest request) {
        // 요청 경로를 추출하여 반환
        return request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }
}
