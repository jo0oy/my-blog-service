package com.jo0oy.myblog.web.api;

import com.jo0oy.myblog.application.service.members.MemberService;
import com.jo0oy.myblog.dto.auth.AuthDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthApiController {

    private final MemberService memberService;


    @PostMapping("/api/v1/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDto.LoginReq loginRequest) {
        log.info("/api/v1/login 호출");

        AuthDto.LoginResponse data = memberService.login(loginRequest.getUsername(), loginRequest.getPassword());

        return ResponseEntity.ok(data);
    }
}
