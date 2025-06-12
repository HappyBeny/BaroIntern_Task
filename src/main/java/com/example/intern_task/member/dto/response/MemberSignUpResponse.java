package com.example.intern_task.member.dto.response;

import com.example.intern_task.member.entity.Member;
import com.example.intern_task.member.entity.UserRole;

public record MemberSignUpResponse(
        Long id,
        String userName,
        String nickname,
        String email,
        String phoneNumber,
        UserRole userRole
) {

    public static MemberSignUpResponse from(Member member) {
        return new MemberSignUpResponse(
                member.getId(),
                member.getUsername(),
                member.getNickname(),
                member.getEmail(),
                member.getPhoneNumber(),
                member.getUserRole()
        );
    }
}
