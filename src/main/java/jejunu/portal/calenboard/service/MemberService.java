package jejunu.portal.calenboard.service;

import jejunu.portal.calenboard.entity.Member;
import jejunu.portal.calenboard.entity.MemberDTO;
import jejunu.portal.calenboard.repository.MemberRepository;
import jejunu.portal.calenboard.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

//    @Transactional
//    public Long joinUser(MemberDTO memberDTO){
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
//        return memberRepository.save(memberDTO.toEntity()).getUid();
//    }


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findById(Long.valueOf(userId)).orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    @Transactional //token을 기반으로 현재 로그인한 유저를 받아옴
    public Member getLoginUser(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        return (Member) authentication.getPrincipal();
    }

}
