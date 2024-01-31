package Baker.community.service;

import Baker.community.domain.PrincipalDetails;
import Baker.community.entity.Member;
import Baker.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional              // 로직 처리중 오류 발생 시 변경된 데이터를 로직 수행 이전으로 콜백
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
                                        // DB에서 회원 정보를 가져오는 역할을 하는 인터페이스(아이디 검증)

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    // 이미 가입된 회원의 경우 예외 발생
    private void validateDuplicateMember(Member member) {
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    // 전달받은 유저 id로 유저를 검색하여 전달하는 메서드
    public Member findById(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("Unexpected user"));
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

/*
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException("없는 회원입니다.");
        }
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().name())
                .build();
} */

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            return new PrincipalDetails(member.get());
        }
        return null;
    }
}
