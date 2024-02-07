package Baker.community.service;

import Baker.community.domain.PrincipalDetails;
import Baker.community.entity.Item;
import Baker.community.entity.ItemImg;
import Baker.community.entity.Member;
import Baker.community.repository.ItemImgRepository;
import Baker.community.repository.ItemRepository;
import Baker.community.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional              // 로직 처리중 오류 발생 시 변경된 데이터를 로직 수행 이전으로 콜백
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;


    public Member saveMember(Member member) {
    validateDuplicateMember(member);
    return memberRepository.save(member);
    }

    // 이미 가입된 회원의 경우 예외 발생
    private void validateDuplicateMember(Member member) {
        Optional<Member> optionalEmil = memberRepository.findByEmail(member.getEmail());
        if (optionalEmil.isPresent()){
            throw new IllegalStateException("joinErrorMsg");
        }
    }

    @Transactional
    public void deleteMember(Long memberId) throws Exception {
        // 1. 회원 아이디로 회원 엔티티 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);

        // 2. 회원이 작성한 레시피(아이템) 목록 조회
        List<Item> itemList = itemRepository.findByMemberId(memberId);

        // 3. 각 레시피(아이템)에 속한 이미지들 삭제
        for (Item item : itemList) {
            List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(item.getId());
            for (ItemImg itemImg : itemImgList) {
                itemImgService.deleteItemImg(itemImg.getId());
            }
        }

        // 4. 회원이 작성한 레시피(아이템)들 삭제
        itemRepository.deleteAllInBatch(itemList);

        // 5. 회원 삭제
        memberRepository.delete(member);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            return new PrincipalDetails(member.get());
        }
        throw new UsernameNotFoundException("User not found with email: " + email);
    }


}


