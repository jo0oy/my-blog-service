package com.jo0oy.myblog.security;

import com.jo0oy.myblog.domain.members.Member;
import com.jo0oy.myblog.domain.members.MemberRepository;
import com.jo0oy.myblog.domain.members.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("존재하지 않는 회원입니다. username={}", username);
                    throw new IllegalArgumentException("존재하지 않는 회원입니다. username=" + username);
                });

        List<Role> roles = List.of(member.getRole());

        return new AuthMember(member.getUsername(), member.getPassword(), roles);
    }
}
