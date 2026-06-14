package com.ex3.khg.member.exception;

// 회원 처리 중 발생할 수 있는 예외 상황과 HTTP 상태 코드를 함께 정의합니다.
public enum MemberExceptions {
    NOT_FOUND("존재하지 않는 회원입니다.", 404),
    DUPLICATE("이미 존재하는 회원입니다.", 409),
    INVALID("유효하지 않은 회원 정보입니다.", 400);

    private MemberTaskException MemberTaskException;

    MemberExceptions(String msg, int code) {
        // enum 값마다 재사용할 사용자 정의 예외 객체를 생성합니다.
        MemberTaskException = new MemberTaskException(msg, code);
    }

    // 컨트롤러나 서비스에서 바로 던질 수 있는 예외 객체를 반환합니다.
    public MemberTaskException get() {
        return MemberTaskException;
    }
}
