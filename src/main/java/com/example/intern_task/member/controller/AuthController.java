package com.example.intern_task.member.controller;

import com.example.intern_task.member.dto.request.SignUpRequest;
import com.example.intern_task.member.dto.request.SignInRequest;
import com.example.intern_task.member.dto.response.SignUpResponse;
import com.example.intern_task.member.dto.response.SignInResponse;
import com.example.intern_task.member.entity.UserRole;
import com.example.intern_task.member.service.AuthService;
import com.example.intern_task.security.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(
            @RequestBody @Valid SignUpRequest memberSignUpRequest
    ) {
       SignUpResponse response = authService.signUp(memberSignUpRequest);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(
            @RequestBody @Valid SignInRequest request
    ) {
        // 1) 인증 시도
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 2) UserDetails 에서 회원 ID, 권한 추출
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Long memberId = Long.parseLong(user.getUsername());
        UserRole role = UserRole.valueOf(
                user.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "")
        );

        // 3) JWT 발급
        String token = jwtUtils.createToken(memberId, role);

        // 4) 응답
        return ResponseEntity.ok(new SignInResponse(token));
    }
}
