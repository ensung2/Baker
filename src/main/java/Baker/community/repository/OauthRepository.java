//package Baker.community.repository;
//
//import Baker.community.entity.Member;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface OauthRepository extends JpaRepository<Member, String> {
//
//    Optional<Member> findByEmail(String email);
//
//    Optional<Member> findByName(String name);
//
//    Optional<Member> findByRefreshToken(String refreshToken);
//}
