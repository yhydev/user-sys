package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.service.MemberService;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberSessionService memberSessionService;

    @RequestMapping("/register")
    public ResponseEntity register(@Validated MemberService.RegisterParam registerParam){
        return memberService.register(registerParam);
    }

    @RequestMapping("/login")
    public ResponseEntity login(@Validated MemberService.LoginParam loginParam){
        return memberService.login(loginParam);
    }

    @RequestMapping("/openAccount")
    public ResponseEntity openAccount(@Validated MemberService.RealNameParam realNameParam){
        return memberService.realName(realNameParam);
    }

    @RequestMapping("/getFrontMember")
    public ResponseEntity getFrontMember(String token){
        return  memberService.getFrontMember(token);
    }



}
