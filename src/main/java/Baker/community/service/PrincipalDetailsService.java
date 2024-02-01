//package Baker.community.service;
//
//import Baker.community.domain.PrincipalDetails;
//import Baker.community.entity.Member;
//import Baker.community.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class PrincipalDetailsService implements UserDetailsService {
//
//    private final MemberRepository memberRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<Member> member = memberRepository.findByEmail(email);
//        if (member.isPresent()){
//            return new PrincipalDetails(member.get());
//        }
//        return null;
//    }
//}
