package Baker.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
