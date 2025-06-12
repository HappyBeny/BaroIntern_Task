package com.example.intern_task.member.service;

import com.example.intern_task.member.dto.request.SignUpRequest;
import com.example.intern_task.member.dto.request.SignInRequest;
import com.example.intern_task.member.dto.response.SignUpResponse;
import com.example.intern_task.member.dto.response.SignInResponse;
import com.example.intern_task.member.entity.Member;
import com.example.intern_task.member.entity.UserRole;
import com.example.intern_task.member.repository.MemberRepository;
import com.example.intern_task.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        validateDuplicatedMember(request);
        String encodedPassword = passwordEncoder.encode(request.password());

        Member member = Member.from(request);
        member.setEncodedPassword(encodedPassword);

        memberRepository.save(member);
        return SignUpResponse.from(member);
    }

    public SignInResponse signIn(SignInRequest request) {
        // 1) 인증
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 2) DB 조회로 ID·권한 가져오기
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        Long memberId = member.getId();
        UserRole role = member.getUserRole();

        // 3) JWT 발급
        String token = jwtUtils.createToken(memberId, role);

        // 4) 응답 DTO 반환
        return new SignInResponse(token);
    }

    private void validateDuplicatedMember(SignUpRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new RuntimeException("");
        }
        if (memberRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new RuntimeException("");
        }
    }
}
