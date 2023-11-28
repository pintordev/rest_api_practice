package com.pintor.rest_api_practice.base.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final ApiAuthenticationEntrypoint apiAuthenticationEntrypoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/api/**") // 아래의 모든 설정 /api/** 경로에만 적용
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(apiAuthenticationEntrypoint)) // 인증 예외 발생 시 이동할 entry point 설정
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/api/*/members/login").permitAll() // /member/login 아무나 접속 가능
                        .requestMatchers("/api/*/articles").permitAll() // /articles 아무나 접속 가능
                        .anyRequest().authenticated()) // 그 외는 인증된 사용자만 접속 가능
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
                .addFilterBefore( // b filter 실행 전 a filter 실행
                        jwtAuthorizationFilter, // 강제 인증 할당 메서드 실행
                        UsernamePasswordAuthenticationFilter.class
                )
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
