package com.january.springsecurity.error.exception;


import com.january.springsecurity.error.ErrorCode;
import lombok.Getter;

/**
 * @Controller, @RestController 가 적용된 Bean 내에서 Exception 발생시 생성되는 Exception 객체
 */

@Getter
public class ControllerException extends RuntimeException{

    private String reason;
    private String code;

    public ControllerException(String reason, String code) {
        this.reason = reason;
        this.code = code;
    }

}
