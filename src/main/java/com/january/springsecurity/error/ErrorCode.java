package com.january.springsecurity.error;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BAD_REQUEST("400", "BAD_REQUEST"),
    FORBIDDEN("403", "FORBIDDEN");

    private final String code;
    private final String message;

}
