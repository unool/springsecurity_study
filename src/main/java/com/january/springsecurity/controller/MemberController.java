package com.january.springsecurity.controller;


import com.january.springsecurity.dto.MemberDTO;

import com.january.springsecurity.error.ErrorResponse;
import com.january.springsecurity.error.exception.ControllerException;
import com.january.springsecurity.error.exception.ExistMemberException;
import com.january.springsecurity.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class MemberController  {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String index()
    {
        return "index";
    }

    @GetMapping("/member/login")
    public String login()
    {
        return "/member/login";
    }

    @GetMapping("/member/register")
    public String register()
    {
        return "/member/register";
    }

    @PostMapping("/member/register")
    public String registerPost(MemberDTO dto, BindingResult bindingResult)
    {
        memberService.registerMember(dto);
        return "redirect:/";
    }

    @GetMapping("/member/admin")
    public String admin() {
        return "/member/admin";
    }

    @GetMapping("/member/user")
    public String user() {
        return "/member/user";
    }

    @ResponseBody
    @PostMapping("/member/exist")
    public ResponseEntity<Boolean> existMember(String id)
    {
        return new ResponseEntity<>(memberService.existMember(id), HttpStatus.OK);
    }

    @ExceptionHandler(ExistMemberException.class)
    public ResponseEntity handleExistMemberException(ExistMemberException e)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setCode(e.getCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ControllerException.class)
    public ResponseEntity<ErrorResponse> handleControllerException(ControllerException e)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getReason());
        errorResponse.setCode(e.getCode());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
