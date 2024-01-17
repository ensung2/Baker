package Baker.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration

/* JPA 에서 Auditing 기능을 사용하여 저장 또는 수정 시 자동으로 날짜 입력 (엔티티의 생성과 수정 감시) */
@EnableJpaAuditing
public class AuditConfig {
}
