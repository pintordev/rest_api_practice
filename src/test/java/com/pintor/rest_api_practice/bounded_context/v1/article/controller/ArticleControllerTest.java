package com.pintor.rest_api_practice.bounded_context.v1.article.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

}