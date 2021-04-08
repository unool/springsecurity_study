package com.january.springsecurity.error.exception;

import lombok.Getter;

/**
 *  가입시, 아이디 체크시에만 생성하여 처리에 사용되는 Exception 객체
 */


@Getter
public class ExistMemberException extends RuntimeException{

    private String message;
    private String code;

    public ExistMemberException(String message, String code) {
        this.message = message;
        this.code = code;
    }

}
