package com.example.intern_task.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank(message = "이름을 입력해주세요")
        String userName,

        @NotBlank(message = "패스워드를 입력해주세요")
        @Size(min = 8, max = 15, message = "비밀번호는 8자 이상 15자 이내여야 합니다.")
        String password,

        @NotBlank(message = "전화번호를 입력해주세요")
        @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "올바른 양식의 전화번호를 입력해주세요")
        String phoneNumber,

        @NotBlank(message = "별명을 입력해주세요.")
        String nickname,

        @Email(message = "올바른 양식의 이메일 주소를 입력해주세요")
        String email
        ) {

}
