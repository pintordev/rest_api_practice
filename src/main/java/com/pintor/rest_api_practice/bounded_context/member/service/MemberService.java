package com.pintor.rest_api_practice.bounded_context.member.service;

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
}
