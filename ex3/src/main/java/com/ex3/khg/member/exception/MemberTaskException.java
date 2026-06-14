package com.ex3.khg.member.exception;

import lombok.Getter;

@Getter
// 회원 도메인에서 사용할 메시지와 상태 코드를 담는 사용자 정의 예외입니다.
public class MemberTaskException extends RuntimeException {
    private String msg;
    private int code;

    public MemberTaskException(String msg, int code) {
        // RuntimeException의 메시지도 함께 설정해 로그나 기본 예외 처리에서 확인할 수 있게 합니다.
        super(msg);
        this.msg = msg;
        this.code = code;
    }
    
}
