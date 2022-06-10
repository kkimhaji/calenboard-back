package jejunu.portal.calenboard.controller;

import jejunu.portal.calenboard.entity.Member;
import jejunu.portal.calenboard.repository.MemberRepository;
import jejunu.portal.calenboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/get")
    public Member get(HttpServletRequest request) throws UserPrincipalNotFoundException {
        Member loginMember = memberService.getLoginUser(request);
        return memberRepository.findByEmail(loginMember.getEmail()).orElseThrow(()->new UserPrincipalNotFoundException("user not found"));
    }


}
