package com.example.intern_task.member.service;

import com.example.intern_task.member.entity.Member;
import com.example.intern_task.member.entity.UserRole;
import com.example.intern_task.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void changeUserRole(Long memberId, UserRole newRole) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다: " + memberId));

        member.setUserRole(newRole);
    }

}
