package com.january.springsecurity.service;


import com.january.springsecurity.dto.MemberDTO;
import com.january.springsecurity.dto.session.SessionUser;
import com.january.springsecurity.entity.Member;
import com.january.springsecurity.entity.role.Role;
import com.january.springsecurity.error.ErrorCode;
import com.january.springsecurity.error.exception.ControllerException;
import com.january.springsecurity.error.exception.ExistMemberException;
import com.january.springsecurity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Spring Security에서 사용되는 UserDetails를 구현한 Member 에대하여 전반적인 처리를 하는 객체
 *
 * 기타
 * 기존에 SecurityConfig에서 auth.userDetailsService로 등록이 되어
 * loadUserByUsername가 로그인시 자동 콜백 되었지만 현재는 CustomAuthenticationProvider로 대체
 */


@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final HttpSession httpSession;
    private final MemberRepository memberRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        Optional<Member> result = memberRepository.findById(id);

        if(!result.isPresent())
        {
            throw new ControllerException("not exist id", ErrorCode.BAD_REQUEST.getCode());
        }

        Member member = result.get();
        httpSession.setAttribute("user",new SessionUser(member));
        return member;
    }

    public Boolean existMember(String id)
    {
        Optional<Member> result = memberRepository.findById(id);

        if(result.isPresent())
        {
            throw new ExistMemberException("exist id", ErrorCode.BAD_REQUEST.getCode());
        }

        String speChar = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";

        Pattern pattern = Pattern.compile(speChar);
        Matcher matcher = pattern.matcher(id);

        if(matcher.find())
        {
            throw new ControllerException("wrong id", ErrorCode.BAD_REQUEST.getCode());
        }
        return memberRepository.existsById(id);
    }

    public String registerMember(MemberDTO dto) {

        Optional<Member> result = memberRepository.findById(dto.getId());

        if(result.isPresent())
        {
            throw new ExistMemberException("exist id", ErrorCode.BAD_REQUEST.getCode());
        }

        Member member = Member.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .auth(Role.USER.getKey())
                .password(encoder.encode(dto.getPassword()))
                .build();

        return memberRepository.save(member).getEmail();
    }




}
