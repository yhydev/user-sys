package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.Application;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.model.FrontMemberSession;
import com.gushushu.yanao.usersys.service.MemberService;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MemberSessionServiceImplTest {


    @Autowired
    MemberSessionService memberSessionService;

    @Autowired
    MemberService memberService;



    String account = "123";
    String password = "123456";
    String accountId = null;
    String token = null;


    @Before
    public void initialize(){

        MemberService.LoginParam loginParam = new MemberService.LoginParam();

        loginParam.setAccount(account);
        loginParam.setPassword(password);

        ResponseEntity<ResponseBody<FrontMemberSession>> memberSessionResponse = memberService.login(loginParam);

        assert  memberSessionResponse.getBody().isSuccess();

        accountId = memberSessionResponse.getBody().getData().getAccount();
        token = memberSessionResponse.getBody().getData().getToken();

    }




    @Test
    public void findSession(){

        memberSessionService.findSession(token);

    }


    @Test
    public void findMemberId() {

        memberSessionService.findMemberId("11111");

    }

    @Test
    public void findMember() {

        memberSessionService.findMember("22222");

    }


    @Test
    public void saveSession() {

        memberSessionService.saveSession("333333");

    }
}