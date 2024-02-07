package Baker.community.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)        // auditing 적용
@MappedSuperclass                                               // 공통 매핑 정보 (상속자에게 정보만 제공)
@Data
public abstract class CreateModify {

    @CreatedDate        // 엔티티가 생성되어 저장될때 시간을 자동으로 저장
    @Column(updatable = false)
    private LocalDateTime regTime;

    @CreatedBy
    @Column(updatable = false)
    private String createBy;

    @LastModifiedDate   // 엔티티의 값을 변경 시 시간을 자동으로 저장
    private LocalDateTime updateTime;
}
