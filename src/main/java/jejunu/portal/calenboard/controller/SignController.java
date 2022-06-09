package jejunu.portal.calenboard.controller;

import jejunu.portal.calenboard.entity.MemberDTO;
import jejunu.portal.calenboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class SignController {

    private final MemberService memberService;

    @GetMapping("/signup") //회원가입 페이지
    public ModelAndView signup(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/signup");

        return modelAndView;
    }

    @PostMapping("/signup") //회원가입 처리
    public ModelAndView signup(MemberDTO memberDTO){
        memberService.joinUser(memberDTO);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/signup_success");
        modelAndView.addObject("info", memberDTO.getNickname());
        return modelAndView;
    }
}
