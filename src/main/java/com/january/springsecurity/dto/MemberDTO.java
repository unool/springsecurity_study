package com.january.springsecurity.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;


@Getter
@Setter
@ToString
public class MemberDTO {

    private String id;

    private String email;

    private String password;

    private String auth;

}
