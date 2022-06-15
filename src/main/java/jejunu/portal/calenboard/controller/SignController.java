package jejunu.portal.calenboard.controller;

import jejunu.portal.calenboard.entity.Member;
import jejunu.portal.calenboard.model.SingleResult;
import jejunu.portal.calenboard.repository.MemberRepository;
import jejunu.portal.calenboard.security.JwtAuthenticationFilter;
import jejunu.portal.calenboard.security.JwtTokenProvider;
import jejunu.portal.calenboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;
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
    public SingleResult<String> signin(@RequestParam String email, @RequestParam String password) throws FailedLoginException {
        return memberService.login(email, password);
    }

    @PostMapping("/signup")
    public Member signup(@RequestParam String email, @RequestParam String password, @RequestParam String nickname) throws Exception {
        return memberService.signup(email, password, nickname);
    }

    @PostMapping("/token")
    public boolean checkToken(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        return jwtTokenProvider.validateToken(token);
    }




}
