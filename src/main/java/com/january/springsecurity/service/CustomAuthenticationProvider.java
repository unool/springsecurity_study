package com.january.springsecurity.service;

import com.january.springsecurity.entity.Member;
import com.january.springsecurity.error.ErrorCode;
import com.january.springsecurity.error.exception.ControllerException;
import com.january.springsecurity.error.exception.ExistMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Spring Security에서 Authenticate 과정에 참여하는 클래스 객체
 *
 * 메소드 설명
 * authenticate : 로그인 시도시 바로 동작하며 UserDetailsService을 구현한 MemberService에서 유저의 데이터를 가져옴.
 *
 */

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MemberService userDeSer;

    @SuppressWarnings("unchecked")
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        Member user = (Member) userDeSer.loadUserByUsername(username);

        Boolean match = encoder.matches(password,user.getPassword());


        if(!match) {
//            throw new BadCredentialsException(username);
            throw new ControllerException("not match info", ErrorCode.BAD_REQUEST.getCode());
        }

        System.out.println("이후");

        if(!user.isEnabled()) {
            throw new BadCredentialsException(username);
        }

        return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }


}


