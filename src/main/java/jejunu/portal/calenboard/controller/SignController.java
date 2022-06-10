package jejunu.portal.calenboard.controller;

import jejunu.portal.calenboard.entity.Member;
import jejunu.portal.calenboard.repository.MemberRepository;
import jejunu.portal.calenboard.security.JwtTokenProvider;
import jejunu.portal.calenboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.FailedLoginException;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SignController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public String signin(@RequestParam String email, @RequestParam String password) throws FailedLoginException {
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("이메일을 다시 한 번 확인해주세요"));
        if(!passwordEncoder.matches(password, member.getPassword())) throw new FailedLoginException("");
        return jwtTokenProvider.createToken(String.valueOf(member.getUid()), member.getRoles());
    }

    @PostMapping("/signup")
    public Long signup(@RequestParam String email, @RequestParam String password, @RequestParam String nickname){
        return memberRepository.save(Member.builder().email(email).nickname(nickname).password(passwordEncoder.encode(password)).roles(Collections.singletonList("ROLE_USER")).build()).getUid();
    }




}
