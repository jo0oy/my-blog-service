package com.jo0oy.myblog.web.api;

import com.jo0oy.myblog.application.service.members.MemberService;
import com.jo0oy.myblog.common.response.ResultResponse;
import com.jo0oy.myblog.dto.members.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public ResponseEntity<?> saveMember(@Valid @RequestBody MemberDto.SaveReq requestDto) {

        log.info("/api/v1/members saveMember 호출");

        Map<String, Long> result = new HashMap<>();
        result.put("savedMemberId", memberService.save(requestDto.toEntity()));

        return ResponseEntity.created(URI.create("/api/v1/member"))
                .body(ResultResponse.success("회원가입이 완료되었습니다.",result));
    }

    @PutMapping("/api/v1/members/{id}")
    public ResponseEntity<?> modifyMemberInfo(@Valid @RequestBody MemberDto.UpdateReq request,
                                              @PathVariable("id") Long id) {
        log.info("/api/v1/members/{} modifyMemberInfo 호출", id);
        log.info("memberUpdateRequest={}", request);

        memberService.modifyMemberInfo(request, id);

        return ResponseEntity.ok()
                .body(ResultResponse.success("회원정보 수정이 정상적으로 완료되었습니다."));
    }
}
