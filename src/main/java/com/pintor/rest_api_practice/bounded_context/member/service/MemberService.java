package com.pintor.rest_api_practice.bounded_context.member.service;

import com.pintor.rest_api_practice.base.jwt.JwtProvider;
import com.pintor.rest_api_practice.bounded_context.member.entity.Member;
import com.pintor.rest_api_practice.bounded_context.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public Member signup(String username, String password, String email) {

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();

        this.memberRepository.save(member);

        return member;
    }

    public Member getUserByUsername(String username) {
        return this.memberRepository.findByUsername(username)
                .orElse(null);
    }

    public String getAccessToken(String username, String password) {

        Member member = this.memberRepository.findByUsername(username)
                .orElse(null);

        if (member == null) return null;

        if (!passwordEncoder.matches(password, member.getPassword())) return null;

        return this.jwtProvider.genToken(member.toClaims(), 60 * 60 * 24 * 365); // 1년 유효 토큰 생성
    }
}
