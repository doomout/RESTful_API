package com.ex3.khg.member.exception;

import lombok.Getter;

@Getter
public class MemberTaskException extends RuntimeException {
    private String msg;
    private int code;

    public MemberTaskException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }
    
}
