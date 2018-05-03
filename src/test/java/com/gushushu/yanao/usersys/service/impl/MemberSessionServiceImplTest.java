package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.Application;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MemberSessionServiceImplTest {


    @Autowired
    MemberSessionService memberSessionService;

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