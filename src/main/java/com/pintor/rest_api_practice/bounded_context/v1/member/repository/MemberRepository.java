package com.pintor.rest_api_practice.bounded_context.v1.member.repository;

import com.pintor.rest_api_practice.bounded_context.v1.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
