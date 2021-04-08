package com.january.springsecurity.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class ErrorResponse {

    private String message;

    private String code;


}
