package com.ex3.khg.member.exception;

public enum MemberExceptions {
    NOT_FOUND("존재하지 않는 회원입니다.", 404),
    DUPLICATE("이미 존재하는 회원입니다.", 409),
    INVALID("유효하지 않은 회원 정보입니다.", 400);

    private MemberTaskException MemberTaskException;

    MemberExceptions(String msg, int code) {
        MemberTaskException = new MemberTaskException(msg, code);
    }

    public MemberTaskException get() {
        return MemberTaskException;
    }
}
