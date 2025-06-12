package com.example.intern_task.member.service;

import com.example.intern_task.member.dto.request.SignUpRequest;
import com.example.intern_task.member.dto.request.SignInRequest;
import com.example.intern_task.member.dto.response.SignUpResponse;
import com.example.intern_task.member.dto.response.SignInResponse;
import com.example.intern_task.member.entity.Member;
import com.example.intern_task.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
        Member foundMember = memberRepository.findByEmail(request.email()).orElseThrow(
                () -> new RuntimeException(""));

        if (!passwordEncoder.matches(request.password(), foundMember.getPassword())) {
            throw new RuntimeException("");
        }

        return new SignInResponse("");
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
