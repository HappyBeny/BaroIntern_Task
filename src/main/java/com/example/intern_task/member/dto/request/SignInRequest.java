package com.example.intern_task.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank(message = "메일 주소를 입력해주세요.")
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {
}
