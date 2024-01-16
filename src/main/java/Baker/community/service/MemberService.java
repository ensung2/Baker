package Baker.community.service;

import Baker.community.entity.Member;
import Baker.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// 로직 처리중 오류 발생 시 변경된 데이터를 로직 수행 이전으로 콜백
@Transactional
// final이나 @NonNull이 붙은 필드에 생성자 생성
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {  // DB에서 회원 정보를 가져오는 역할을 하는 인터페이스

    private final MemberRepository memberRepository;


    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    // 이미 가입된 회원의 경우 예외 발생
    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException(email);
        }
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
