package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.Application;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.IdentifyingCode;
import com.gushushu.yanao.usersys.repository.IdentifyingCodeRepository;
import com.gushushu.yanao.usersys.service.IdentifyingCodeService;
import com.gushushu.yanao.usersys.service.MemberService;
import org.apache.catalina.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MemberServiceImplTest {


    @Autowired
    private MemberService memberService;

    @Autowired
    private IdentifyingCodeRepository identifyingCodeRepository;

    @Autowired
    private IdentifyingCodeService identifyingCodeService;


    String account = "123";
    String password = "123456";

    @Test
    public void register() {



        MemberService.RegisterParam registerParam = new MemberService.RegisterParam();

        //错误验证码注册

        registerParam.setAccount(account);
        registerParam.setPassword(password);
        registerParam.setPhoneCode(416645);
        memberService.register(registerParam);

        //正确验证码注册
        IdentifyingCodeService.SendParam sendParam = new IdentifyingCodeService.SendParam();
        sendParam.type = MemberService.VCODE_TYPE_REGISTER;
        sendParam.phone = account;
        ResponseEntity<ResponseBody<String>> rep =  identifyingCodeService.send(sendParam);

        //get send sms code for before
        IdentifyingCode code = identifyingCodeRepository.findOne(rep.getBody().getData());


        registerParam.setPhoneCode(code.getCode());
        memberService.register(registerParam);

    }

    @Test
    public void login() {


    }
}