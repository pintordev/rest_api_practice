package com.pintor.rest_api_practice.bounded_context.v1.article.service;

import com.pintor.rest_api_practice.bounded_context.v1.article.entity.Article;
import com.pintor.rest_api_practice.bounded_context.v1.article.repository.ArticleRepository;
import com.pintor.rest_api_practice.bounded_context.v1.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> getList() {
        return this.articleRepository.findAll();
    }

    public Article post(Member author, String title, String content) {

        Article article = Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        this.articleRepository.save(article);

        return article;
    }
}
