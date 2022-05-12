package com.jo0oy.myblog.application.service.members;

import com.jo0oy.myblog.domain.members.Member;
import com.jo0oy.myblog.dto.auth.AuthDto;
import com.jo0oy.myblog.dto.members.MemberDto;

public interface MemberService {

    Long save(Member member);

    MemberDto.MemberResponse memberDetail(Long memberId);

    MemberDto.MemberResponse memberDetail(String username);

    AuthDto.LoginResponse login(String username, String password);

    void modifyMemberInfo(MemberDto.UpdateReq request, Long memberId);
}
