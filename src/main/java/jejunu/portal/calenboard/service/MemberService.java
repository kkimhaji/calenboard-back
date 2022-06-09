package jejunu.portal.calenboard.service;

import jejunu.portal.calenboard.entity.Member;
import jejunu.portal.calenboard.entity.MemberDTO;
import jejunu.portal.calenboard.entity.Role;
import jejunu.portal.calenboard.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private MemberRepository memberRepository;

    @Transactional
    public Long joinUser(MemberDTO memberDTO){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        return memberRepository.save(memberDTO.toEntity()).getUid();
    }

//    public String getEmail

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> user = memberRepository.findByEmail(username);
        Member member = user.get();

        List<GrantedAuthority> authorityList = new ArrayList<>();

        if(("admin").equals(username)) authorityList.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        else authorityList.add(new SimpleGrantedAuthority(Role.USER.getValue()));

        return new User(member.getEmail(), member.getPassword(), authorityList);
    }
}
