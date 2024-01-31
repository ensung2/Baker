package Baker.community.repository;

import Baker.community.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 회원가입 시 중복된 회원이 있는지 검사 (중복 이메일은 가입 불가)
    Optional<Member> findByEmail(String email);

    Optional<Member> findById(Long id);

    public Member findByName(String name);


}
