//package Baker.community.service;
//
//import Baker.community.dto.JoinMemberDto;
//import Baker.community.entity.Member;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//@TestPropertySource(locations = "classpath:application-test.properties")
//class MemberServiceTest {
//
//    @Autowired
//    MemberService memberService;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//
//    // 임의의 회원 생성
//    public Member createMember(){
//        JoinMemberDto memberFormDto = new JoinMemberDto();
//        memberFormDto.setUsername("유저");
//        memberFormDto.setEmail("user@email.com");
//        memberFormDto.setPassword("1234");
//        return Member.createMember(memberFormDto, passwordEncoder);
//    }
//
//    @Test
//    @DisplayName("회원 가입 테스트")
//    public void saveMemberTest(){
//        Member member = createMember();
//        Member savedMember = memberService.saveMember(member);
//
//        assertEquals(member.getUsername(), savedMember.getUsername());
//        assertEquals(member.getEmail(), savedMember.getEmail());
//        assertEquals(member.getPassword(), savedMember.getPassword());
//        assertEquals(member.getRole(), savedMember.getRole());
//    }
//
//    @Test
//    @DisplayName("중복 회원 가입 테스트")
//    public void saveDuplicateMemberTest() {
//        Member member1 = createMember();
//        Member member2 = createMember();
//        memberService.saveMember(member1);
//
//        // 1) member1과 member2가 같을경우
//        Throwable e = assertThrows(IllegalStateException.class, () -> {
//            memberService.saveMember(member2);});
//
//        // 2) 해당 에러메세지가 뜹니다!
//        assertEquals("이미 가입된 회원입니다.", e.getMessage());
//    }
//
//}