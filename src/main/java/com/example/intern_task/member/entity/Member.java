package com.example.intern_task.member.entity;

import com.example.intern_task.global.entity.BaseEntity;
import com.example.intern_task.member.dto.request.SignUpRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String username;
    String password;
    @Column(unique = true)
    String phoneNumber;
    String nickname;
    @Column(unique = true)
    String email;
    UserRole userRole;


    private Member(String username, String password, String phoneNumber, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.email = email;
        userRole = UserRole.USER;
    }

    public static Member from (SignUpRequest request) {
        return new Member(
                request.userName(),
                request.password(),
                request.phoneNumber(),
                request.nickname(),
                request.email()
        );
    }

    public void setEncodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
