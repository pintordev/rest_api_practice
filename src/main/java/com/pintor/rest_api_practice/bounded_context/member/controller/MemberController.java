package com.pintor.rest_api_practice.bounded_context.member.controller;

import com.pintor.rest_api_practice.bounded_context.member.entity.Member;
import com.pintor.rest_api_practice.bounded_context.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/member", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE) // 입출력 json
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @Getter
    @Setter
    public static class LoginRequest {

        @NotBlank // null, "", " " 불허
        private String username;

        @NotBlank
        private String password;
    }

    @PostMapping("/login")
    public Member login(@Valid @RequestBody LoginRequest loginRequest) {

        return this.memberService.getUserByUsername(loginRequest.getUsername());
    }
}
