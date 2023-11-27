package com.pintor.rest_api_practice.bounded_context.v1.member.service;

import com.pintor.rest_api_practice.base.jwt.JwtProvider;
import com.pintor.rest_api_practice.bounded_context.v1.member.entity.Member;
import com.pintor.rest_api_practice.bounded_context.v1.member.repository.MemberRepository;
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

    public String getAccessToken(Member member) {

        return this.jwtProvider.genToken(member.toClaims(), 60 * 60 * 24 * 365); // 1년 유효 토큰 생성
    }

    public boolean isPasswordMatched(String encoded, String raw) {
        return this.passwordEncoder.matches(raw, encoded);
    }
}
