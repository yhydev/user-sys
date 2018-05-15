package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.service.MemberSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memberSession")
public class MemberSessionController {


    @Autowired
    private MemberSessionService memberSessionService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getMember(String token){
        return memberSessionService.findSession(token);
    }



}
