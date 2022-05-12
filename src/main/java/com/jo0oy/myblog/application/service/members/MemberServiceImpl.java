package com.jo0oy.myblog.application.service.members;

import com.jo0oy.myblog.common.exception.LoginFailException;
import com.jo0oy.myblog.domain.members.Member;
import com.jo0oy.myblog.domain.members.MemberRepository;
import com.jo0oy.myblog.dto.auth.AuthDto;
import com.jo0oy.myblog.dto.members.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Long save(Member member) {
        validateDuplicateUsername(member.getUsername());
        member.setEncodedPassword(passwordEncoder.encode(member.getPassword()));

        return memberRepository.save(member).getId();
    }

    @Override
    public MemberDto.MemberResponse memberDetail(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.error("존재하지 않는 회원입니다. id={}", memberId);
                    throw new IllegalArgumentException("존재하지 않는 회원입니다. id=" + memberId);
                });

        return new MemberDto.MemberResponse(member);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDto.MemberResponse memberDetail(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("존재하지 않는 회원입니다. username={}", username);
                    throw new IllegalArgumentException("존재하지 않는 회원입니다. username=" + username);
                });

        return new MemberDto.MemberResponse(member);
    }

    private void validateDuplicateUsername(String username) {
        Member findMember = memberRepository.findByUsername(username).orElse(null);

        if (findMember != null) {
            log.error("이미 존재하는 아이디입니다. username={}", username);
            throw new RuntimeException("이미 존재하는 아이디입니다. username=" + username);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AuthDto.LoginResponse login(String username, String password) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("존재하지 않는 회원입니다. username={}", username);
                    throw new LoginFailException("아이디, 비밀번호가 일치하지 않습니다.");
                });

        if (!member.getPassword().equals(password)) {
            throw new LoginFailException("아이디, 비밀번호가 일치하지 않습니다.");
        }

        return new AuthDto.LoginResponse(member.getUsername());
    }

    @Override
    @Transactional
    public void modifyMemberInfo(MemberDto.UpdateReq request, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> {
            log.error("존재하지 않는 회원입니다. id={}", memberId);
            throw new IllegalArgumentException("존재하지 않는 회원입니다. id=" + memberId);
        });

        String password = request.getPassword();

        if (password.equals("defaultPassword00")) {
            password = member.getPassword();
        } else {
            password = passwordEncoder.encode(request.getPassword());
        }

        member.update(password, request.getName(), request.getEmail(), request.getPhoneNumber());
    }
}
