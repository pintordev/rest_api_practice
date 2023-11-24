package com.pintor.rest_api_practice.base.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors
                        .disable()) // 타 도메인에서 API 호출 가능
                .csrf(csrf -> csrf
                        .disable()) // CSRF 토큰 끄기
                .httpBasic(httpBasic -> httpBasic
                        .disable()) // httpBasic 로그인 방식 끄기
                .formLogin(formLogin -> formLogin
                        .disable()) // 폼 로그인 방식 끄기
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 끄기
        ;

        return http.build();
    }
}
