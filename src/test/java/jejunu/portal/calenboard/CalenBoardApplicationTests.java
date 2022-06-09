package jejunu.portal.calenboard;

import jejunu.portal.calenboard.entity.Member;
import jejunu.portal.calenboard.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class CalenBoardApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void testRead(){
        Optional<Member> result = memberRepository.findByEmail("user3@jejunu.ac.kr");
        Member member = result.get();
        System.out.println(member);
    }

}
