package com.pintor.rest_api_practice.bounded_context.v1.article.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("get:/api/v1/articles")
    public void t1() throws Exception {

        // given

        // when
        ResultActions resultActions = this.mockMvc
                .perform(get("/api/v1/articles"))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.code").value("S-1"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data.articles[0].id").exists());
    }

    @Test
    @DisplayName("get:/api/v1/articles/1")
    public void t2() throws Exception {

        // given

        // when
        ResultActions resultActions = this.mockMvc
                .perform(get("/api/v1/articles/1"))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.code").value("S-1"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data.article.id").value(1));
    }

    @Test
    @WithUserDetails("user1")
    @DisplayName("post:api/v1/articles")
    public void t3() throws Exception {

        // given

        // when
        ResultActions resultActions = this.mockMvc
                .perform(post("/api/v1/articles")
                        .content("""
                                {
                                    "title": "title t3",
                                    "content": "content t3"
                                }
                                """.stripIndent())
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.code").value("S-1"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data.article").exists());
    }

    @Test
    @WithUserDetails("user1")
    @DisplayName("patch:api/v1/articles/4")
    public void t4() throws Exception {

        // given

        // when
        ResultActions resultActions = this.mockMvc
                .perform(patch("/api/v1/articles/4")
                        .content("""
                                {
                                    "title": "title t4",
                                    "content": "content t4"
                                }
                                """.stripIndent())
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.code").value("S-1"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data.article.id").value(4))
                .andExpect(jsonPath("$.data.article.title").value("title t4"))
                .andExpect(jsonPath("$.data.article.content").value("content t4"));
    }

    @Test
    @WithUserDetails("user1")
    @DisplayName("patch:api/v1/articles/4; partial modification")
    public void t5() throws Exception {

        // given

        // when
        ResultActions resultActions = this.mockMvc
                .perform(patch("/api/v1/articles/4")
                        .content("""
                                {
                                    "content": "content t4"
                                }
                                """.stripIndent())
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.code").value("S-1"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data.article.id").value(4))
                .andExpect(jsonPath("$.data.article.title").value("title4"))
                .andExpect(jsonPath("$.data.article.content").value("content t4"));
    }
}