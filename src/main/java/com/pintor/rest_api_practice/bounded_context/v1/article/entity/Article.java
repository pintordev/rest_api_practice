package com.pintor.rest_api_practice.bounded_context.v1.article.entity;

import com.pintor.rest_api_practice.base.entity.BaseEntity;
import com.pintor.rest_api_practice.bounded_context.v1.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
public class Article extends BaseEntity {

    private String title;

    private String content;

    @ManyToOne
    private Member author;
}
