package com.pintor.rest_api_practice.bounded_context.v1.member.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("post:/api/v1/member/login")
    public void t1() throws Exception {

        // given

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/member/login")
                        .content("""
                                {
                                    "username": "user1",
                                    "password": "1234"
                                }
                                """.stripIndent())
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @DisplayName("post:/api/v1/member/login; include accessToken in response header")
    public void t2() throws Exception {

        // given

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/member/login")
                        .content("""
                                {
                                    "username": "user1",
                                    "password": "1234"
                                }
                                """.stripIndent())
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful());

        // 요청 결과에서 응답 헤더에 포함된 accessToken 추출 과정
        MvcResult result = resultActions.andReturn(); // 결과 리턴
        MockHttpServletResponse response =  result.getResponse(); // 응답 추출
        String accessToken = response.getHeader("accessToken"); // 응답 헤더에서 accessToken 추출

        assertThat(accessToken).isNotEmpty();
    }

    @Test
    @DisplayName("post:/api/v1/member/login; assert json response body")
    public void t3() throws Exception {

        // given

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/member/login")
                        .content("""
                                {
                                    "username": "user1",
                                    "password": "1234"
                                }
                                """.stripIndent())
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.code").value("S-1"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data.accessToken").exists());

    }

    @Test
    @DisplayName("get:/api/v1/member/me; inquire user information")
    public void t4() throws Exception {

        // given

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/member/me"))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.code").value("S-1"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data.member.id").exists())
                .andExpect(jsonPath("$.data.member.username").exists());
    }
}