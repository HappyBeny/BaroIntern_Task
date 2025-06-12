package com.example.intern_task.member.dto.request;

import com.example.intern_task.member.entity.UserRole;
import jakarta.validation.constraints.NotBlank;

public record ChangeUserRoleRequest(
        @NotBlank(message = "역할을 변경하실 회원 ID를 입력해주세요")
        Long memberId,

        @NotBlank(message = "새로 부여하실 역할을 입력해주세요.")
        UserRole newRole
) {

}

