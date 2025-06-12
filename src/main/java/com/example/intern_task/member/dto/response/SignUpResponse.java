package com.example.intern_task.member.dto.response;

import com.example.intern_task.member.entity.Member;
import com.example.intern_task.member.entity.UserRole;

public record SignUpResponse(
        Long id,
        String userName,
        String nickname,
        String email,
        String phoneNumber,
        UserRole userRole
) {

    public static SignUpResponse from(Member member) {
        return new SignUpResponse(
                member.getId(),
                member.getUsername(),
                member.getNickname(),
                member.getEmail(),
                member.getPhoneNumber(),
                member.getUserRole()
        );
    }
}
