package com.pintor.rest_api_practice.bounded_context.v1.article.service;

import com.pintor.rest_api_practice.bounded_context.v1.article.entity.Article;
import com.pintor.rest_api_practice.bounded_context.v1.article.repository.ArticleRepository;
import com.pintor.rest_api_practice.bounded_context.v1.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Article> getById(Long id) {

        return this.articleRepository.findById(id);
    }

    public Article patch(Article article, String title, String content) {

        article = article.toBuilder()
                .title(title != null ? title : article.getTitle())
                .content(content != null ? content : article.getContent())
                .build();

        this.articleRepository.save(article);

        return article;
    }

    public void delete(Article article) {
        this.articleRepository.delete(article);
    }
}
