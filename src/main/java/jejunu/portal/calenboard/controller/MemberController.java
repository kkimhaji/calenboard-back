package jejunu.portal.calenboard.controller;

import jejunu.portal.calenboard.entity.Member;
import jejunu.portal.calenboard.repository.MemberRepository;
import jejunu.portal.calenboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/get")
    public Optional<Member> get(HttpServletRequest request) {
        return memberService.get(request);
    }

    @DeleteMapping("/delete")
    public Long delete(HttpServletRequest request){
        return memberService.delete(request);
    }

    @DeleteMapping("/delete/{uid}")
    public Long deletebyUid(@PathVariable Long uid){
        return memberService.deletebyUid(uid);
    }




}
