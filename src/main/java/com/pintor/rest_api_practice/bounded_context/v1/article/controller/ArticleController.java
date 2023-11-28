package com.pintor.rest_api_practice.bounded_context.v1.article.controller;

import com.pintor.rest_api_practice.base.rsData.RsData;
import com.pintor.rest_api_practice.bounded_context.v1.article.entity.Article;
import com.pintor.rest_api_practice.bounded_context.v1.article.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "api.v1.article_controller", description = "글 CRUD") // 컨트롤러 이름, 설명
@RequestMapping(value = "/api/v1/articles", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE) // 입출력 json
@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @Getter
    @AllArgsConstructor
    public static class ArticlesResponse {
        private final List<Article> articles;
    }

    @Operation(summary = "게시글 목록 조회") // 메서드 설명
    @GetMapping(consumes = ALL_VALUE)
    public RsData<ArticlesResponse> articles() {

        List<Article> articles = this.articleService.getList();

        return RsData.of(
                "S-1",
                "게시글 목록을 반환했습니다",
                new ArticlesResponse(articles)
        );
    }

}
