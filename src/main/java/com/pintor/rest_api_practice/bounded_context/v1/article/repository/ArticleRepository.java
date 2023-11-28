package com.pintor.rest_api_practice.bounded_context.v1.article.repository;

import com.pintor.rest_api_practice.bounded_context.v1.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
