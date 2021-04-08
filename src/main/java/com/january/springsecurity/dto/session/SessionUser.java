package com.january.springsecurity.dto.session;

import com.january.springsecurity.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String id, email, auth;

    public SessionUser(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.auth = member.getAuth();
    }
}
