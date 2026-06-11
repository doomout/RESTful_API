package com.todo.khg.member.exception;

import lombok.Getter;

@Getter
public class MemberTaskExceptiopn extends RuntimeException {
    private String msg;
    private int code;

    public MemberTaskExceptiopn(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }
    
}
