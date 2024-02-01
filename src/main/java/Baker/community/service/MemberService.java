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

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
    validateDuplicateMember(member);
    return memberRepository.save(member);
    }

    // 이미 가입된 회원의 경우 예외 발생
    private void validateDuplicateMember(Member member) {
        Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());
        if (optionalMember.isPresent()){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            return new PrincipalDetails(member.get());
        }return null;
    }




}
