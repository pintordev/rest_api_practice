package com.pintor.rest_api_practice.bounded_context.member.repository;

import com.pintor.rest_api_practice.bounded_context.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
