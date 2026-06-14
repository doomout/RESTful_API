package com.ex3.khg.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_members")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
public class MemberEntity {
    // 회원 아이디를 기본키로 사용합니다.
    @Id
    private String mid;

    // 암호화된 비밀번호, 이름, 이메일 정보를 저장합니다.
    private String mpw;
    private String mname;
    private String email;

    // 엔티티가 처음 저장될 때 가입 일시가 자동으로 입력됩니다.
    @CreatedDate
    private LocalDateTime joinDate;

    // 엔티티가 수정될 때마다 마지막 수정 일시가 자동으로 갱신됩니다.
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    // 회원 권한을 저장합니다. 예: 일반 회원, 관리자
    private String role;

    // 비밀번호 변경 시 엔티티 내부 상태만 갱신합니다.
    public void changePassword(String password) {
        this.mpw = password;
    }

    // 회원 이름을 변경합니다.
    public void changeName(String name) {
        this.mname = name;
    }

    // 이메일 주소를 변경합니다.
    public void changeEmail(String email) {
        this.email = email;
    }

    // 회원 권한을 변경합니다.
    public void changeRole(String role) {
        this.role = role;
    }
}
