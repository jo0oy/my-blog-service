package com.jo0oy.myblog.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class AuthController {

    // 로그인 페이지
    @GetMapping("/auth/login")
    public String loginForm() {
        return "/auth/loginForm";
    }

    // 로그인 에러 페이지
    @GetMapping("/auth/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);

        return "/auth/loginForm";
    }

    // 회원가입 페이지
    @GetMapping("/auth/join")
    public String joinForm() {
        return "/auth/joinForm";
    }
}
