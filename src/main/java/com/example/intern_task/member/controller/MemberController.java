package com.example.intern_task.member.controller;

import com.example.intern_task.member.dto.request.ChangeUserRoleRequest;
import com.example.intern_task.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admin/user-role")
    public ResponseEntity<Void> changeUserRole(
            @RequestBody ChangeUserRoleRequest request
    ) {
        memberService.changeUserRole(request.memberId(), request.newRole());
        return ResponseEntity.ok().build();
    }

}
