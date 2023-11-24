package com.pintor.rest_api_practice.base.init_data;

import com.pintor.rest_api_practice.bounded_context.member.entity.Member;
import com.pintor.rest_api_practice.bounded_context.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
@Profile({"dev", "test"})
public class NotProd {

    private final MemberService memberService;

    @Bean
    CommandLineRunner initData(MemberService memberService) {

        return args -> {
            Member member1 = memberService.signup("user1", "1234", "user1@test.com");
            Member member2 = memberService.signup("user2", "1234", "user2@test.com");
        };
    }
}
