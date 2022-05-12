package com.jo0oy.myblog.web;

import com.jo0oy.myblog.application.service.members.MemberService;
import com.jo0oy.myblog.dto.members.MemberDto;
import com.jo0oy.myblog.security.AuthMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

/*    @PostMapping("/members/join")
    public String joinMember(@Valid @ModelAttribute("member") JoinForm joinForm, BindingResult result) {

        log.info("post /members/join 진입");

        System.out.println(joinForm);

        if (result.hasErrors()) {
            log.info("errors={}", result);
            return "members/joinForm";
        }

        log.info("joinForm 검증 성공");
        // 회원가입 검증 통과 로직
        memberService.save(joinForm.toEntity());

        return "redirect:/";
    } */

    // 내 정보 조회 페이지
    @GetMapping("/members/my-info")
    public String myPage(Model model, @AuthenticationPrincipal AuthMember authMember) {
        MemberDto.MemberResponse data = memberService.memberDetail(authMember.getUsername());

        model.addAttribute("member", data);

        return "/members/myPage";
    }

    // 내 정보 수정 페이지
    @GetMapping("/members/my-info/modify")
    public String updateMyPage(Model model, @AuthenticationPrincipal AuthMember authMember) {
        MemberDto.MemberResponse data = memberService.memberDetail(authMember.getUsername());

        model.addAttribute("member", data);

        return "/members/modifyForm";
    }
}
