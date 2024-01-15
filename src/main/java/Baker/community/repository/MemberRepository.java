package Baker.community.repository;

import Baker.community.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

    // 회원가입 시 중복된 회원이 있는지 검사 (중복 이메일은 가입 불가)
    Member findByEmail(String email);
}