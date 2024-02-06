package Baker.community.config;

import Baker.community.domain.PrincipalDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(ObjectUtils.isEmpty(authentication) || !(authentication.getPrincipal() instanceof PrincipalDetails)){
            return Optional.empty();
        }

        return Optional.of(((PrincipalDetails)authentication.getPrincipal()).getUsername());
    }
}
