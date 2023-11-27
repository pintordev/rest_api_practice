package com.pintor.rest_api_practice.bounded_context.v1.member.controller;

import com.pintor.rest_api_practice.base.rsData.RsData;
import com.pintor.rest_api_practice.bounded_context.v1.member.entity.Member;
import com.pintor.rest_api_practice.bounded_context.v1.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "api.v1.member_controller", description = "회원 로그인, 엑세스 토큰 발급, 로그인한 회원 정보 조회") // 컨트롤러 이름, 설명
@RequestMapping(value = "/api/v1/member", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE) // 입출력 json
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @Getter
    public static class LoginRequest {

        @NotBlank // null, "", " " 불허
        private String username;

        @NotBlank
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class LoginResponse {

        private final String accessToken;
    }

    @Operation(summary = "로그인, 성공 시 엑세스 토큰 발급") // 메서드 설명
    @PostMapping("/login")
    public RsData<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest,
                        HttpServletResponse response) {

        Member member = this.memberService.getUserByUsername(loginRequest.getUsername());

        // 로그인 ID 검증
        if (member == null) return RsData.of("F-1", "존재하지 않는 회원입니다");

        // 비밀번호 검증
        if (!this.memberService.isPasswordMatched(member.getPassword(), loginRequest.getPassword()))
            return RsData.of("F-2", "비밀번호가 일치하지 않습니다");

        String accessToken = this.memberService.getAccessToken(member);

        return RsData.of(
                "S-1",
                "액세스 토큰이 생성되었습니다",
                new LoginResponse(accessToken)
        );
    }

    @Getter
    @AllArgsConstructor
    public static class MeResponse {

        private final Member member;
    }

    @Operation(summary = "로그인한 회원 정보 조회", security = @SecurityRequirement(name = "bearerAuth")) // 메서드 설명
    @GetMapping(value = "/me", consumes = ALL_VALUE)
    public RsData<MeResponse> me(@AuthenticationPrincipal User user) { // 로그인 된 사용자 받아옴

        Member member = this.memberService.getUserByUsername(user.getUsername());

        return RsData.of(
                "S-1",
                "회원 정보를 반환했습니다",
                new MeResponse(member)
        );
    }
}
