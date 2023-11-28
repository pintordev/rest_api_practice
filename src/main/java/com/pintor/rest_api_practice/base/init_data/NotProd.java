package com.pintor.rest_api_practice.base.init_data;

import com.pintor.rest_api_practice.bounded_context.v1.article.entity.Article;
import com.pintor.rest_api_practice.bounded_context.v1.article.service.ArticleService;
import com.pintor.rest_api_practice.bounded_context.v1.member.entity.Member;
import com.pintor.rest_api_practice.bounded_context.v1.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
@Profile({"dev", "test"})
public class NotProd {

    private final MemberService memberService;
    private final ArticleService articleService;

    @Bean
    CommandLineRunner initData(MemberService memberService) {

        return args -> {
            Member admin = this.memberService.signup("admin", "1234", "admin@test.com");
            Member member1 = this.memberService.signup("user1", "1234", "user1@test.com");
            Member member2 = this.memberService.signup("user2", "1234", "user2@test.com");

            Article article1 = articleService.post(admin, "title1", "content1");
            Article article2 = articleService.post(admin, "title2", "content2");
            Article article3 = articleService.post(admin, "title3", "content3");

            Article article4 = articleService.post(member1, "title4", "content4");
            Article article5 = articleService.post(member1, "title5", "content5");
            Article article6 = articleService.post(member1, "title6", "content6");

            Article article7 = articleService.post(member2, "title7", "content7");
            Article article8 = articleService.post(member2, "title8", "content8");
            Article article9 = articleService.post(member2, "title9", "content9");
        };
    }
}
