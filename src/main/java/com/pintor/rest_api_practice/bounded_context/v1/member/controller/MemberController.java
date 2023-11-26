package com.pintor.rest_api_practice.bounded_context.v1.member.controller;

import com.pintor.rest_api_practice.base.rsData.RsData;
import com.pintor.rest_api_practice.bounded_context.v1.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @PostMapping("/login")
    public RsData<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest,
                        HttpServletResponse response) {

        String accessToken = this.memberService.getAccessToken(loginRequest.getUsername(), loginRequest.getPassword());

        return RsData.of(
                "S-1",
                "액세스 토큰이 생성되었습니다",
                new LoginResponse(accessToken)
        );
    }
}
