package com.pintor.rest_api_practice.bounded_context.v1.article.controller;

import com.pintor.rest_api_practice.base.rsData.RsData;
import com.pintor.rest_api_practice.bounded_context.v1.article.entity.Article;
import com.pintor.rest_api_practice.bounded_context.v1.article.service.ArticleService;
import com.pintor.rest_api_practice.bounded_context.v1.member.entity.Member;
import com.pintor.rest_api_practice.bounded_context.v1.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "api.v1.article_controller", description = "글 CRUD") // 컨트롤러 이름, 설명
@RequestMapping(value = "/api/v1/articles", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
// 입출력 json
@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;

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

    @Getter
    @AllArgsConstructor
    public static class ArticleResponse {
        private final Article article;
    }

    @Operation(summary = "게시글 단건 조회")
    @GetMapping(value = "/{id}", consumes = ALL_VALUE)
    public RsData<ArticleResponse> article(@PathVariable("id") Long id) {

        // 최근 경향
        return this.articleService.getById(id)
                .map(article ->
                        RsData.of(
                                "S-1",
                                "%d번 게시글을 반환했습니다".formatted(id),
                                new ArticleResponse(article)
                        )
                )
                .orElseGet(() ->
                        RsData.of(
                                "F-1",
                                "%d번 게시글이 존재하지 않습니다".formatted(id),
                                null
                        )
                );
    }

    @Getter
    public static class PostRequest {

        @NotBlank
        private String title;

        @NotBlank
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class PostResponse {
        private final Article article;
    }

    @Operation(summary = "게시글 등록", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public RsData<PostResponse> post(@AuthenticationPrincipal User user,
                                     @Valid @RequestBody PostRequest postRequest) {

        Member member = this.memberService.getUserByUsername(user.getUsername());

        Article article = this.articleService.post(member, postRequest.getTitle(), postRequest.getContent());

        return RsData.of(
                "S-1",
                "게시물이 생성되었습니다",
                new PostResponse(article)
        );
    }
}
