package jejunu.portal.calenboard.service;

import jejunu.portal.calenboard.entity.Member;
import jejunu.portal.calenboard.repository.MemberRepository;
import jejunu.portal.calenboard.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.CommunicationException;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;
import java.security.Security;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public Optional<String> login(String email, String password) throws FailedLoginException {
        Member member = memberRepository.findByEmail(email).orElseThrow();
        if(!passwordEncoder.matches(password, member.getPassword())) throw new FailedLoginException("로그인 실패");
        String result = jwtTokenProvider.createToken(String.valueOf(member.getUid()), member.getRoles());
        SecurityContextHolder.clearContext();
        return Optional.ofNullable(result);
    }

    @Transactional //token을 기반으로 현재 로그인한 유저를 받아옴
    public Member getLoginUser(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        return (Member) authentication.getPrincipal();
    }

    public Optional<Member> get(HttpServletRequest request){
        Member loginMem = getLoginUser(request);
        return memberRepository.findById(loginMem.getUid());
    }

    public Member signup(String email, String password, String nickname) throws Exception {
        if(memberRepository.findByEmail(email).isPresent()) {
            throw new CommunicationException("already existed email");
        }
        return memberRepository.save(Member.builder().email(email).nickname(nickname).password(passwordEncoder.encode(password)).roles(Collections.singletonList("ROLE_USER")).build());
    }

    public Long delete(HttpServletRequest request){
        Member member = getLoginUser(request);
        Long userId = member.getUid();
        memberRepository.deleteById(userId);
        return userId;
    }

    public Long deletebyUid(Long uid){
        memberRepository.deleteById(uid);
        return uid;
    }
}
