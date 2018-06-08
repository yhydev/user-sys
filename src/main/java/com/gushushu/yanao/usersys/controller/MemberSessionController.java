package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.common.annotation.HandlerRole;
import com.gushushu.yanao.usersys.entity.MemberSession;
import com.gushushu.yanao.usersys.service.MemberService;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import com.gushushu.yanao.usersys.service.impl.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.gushushu.yanao.usersys.common.QBeans.*;

@RestController
@RequestMapping("/memberSession")
public class MemberSessionController {

    @Autowired
    private MemberSessionService memberSessionService;


    @GetMapping("/findOne")
    @HandlerRole({MemberServiceImpl.MemberType.MANAGER_TYPE, MemberServiceImpl.MemberType.USER_TYPE})
    public ResponseEntity findOne(@CookieValue("token") String token){
        MemberSessionService.FindOneParam findOneParam = new MemberSessionService.FindOneParam(MEMBER_SESSION_Q_BEAN);
        findOneParam.setEqToken(token);
        return memberSessionService.findOne(findOneParam);
    }


}
