package com.example.intern_task.member.controller;

import com.example.intern_task.member.dto.request.SignUpRequest;
import com.example.intern_task.member.dto.request.SignInRequest;
import com.example.intern_task.member.dto.response.SignUpResponse;
import com.example.intern_task.member.dto.response.SignInResponse;
import com.example.intern_task.member.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(
            @RequestBody @Valid SignUpRequest request
    ) {
       SignUpResponse response = authService.signUp(request);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<SignInResponse> signIn(
            @RequestBody @Valid SignInRequest request
    ) {
        SignInResponse response = authService.signIn(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
