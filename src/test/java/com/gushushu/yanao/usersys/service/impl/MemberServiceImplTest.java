package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.Application;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.IdentifyingCode;
import com.gushushu.yanao.usersys.entity.MemberSession;
import com.gushushu.yanao.usersys.repository.IdentifyingCodeRepository;
import com.gushushu.yanao.usersys.repository.MemberRepository;
import com.gushushu.yanao.usersys.service.IdentifyingCodeService;
import com.gushushu.yanao.usersys.service.MemberService;
import org.apache.catalina.User;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MemberServiceImplTest {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private IdentifyingCodeRepository identifyingCodeRepository;

    @Autowired
    private IdentifyingCodeService identifyingCodeService;


    String account = "123";
    String password = "123456";
    String accountId = null;


    @Before
    public void initialize(){

        MemberService.LoginParam loginParam = new MemberService.LoginParam();

        loginParam.setAccount(account);
        loginParam.setPassword(password);

        ResponseEntity<ResponseBody<MemberSession>> memberSessionResponse = memberService.login(loginParam);

        assert  memberSessionResponse.getBody().isSuccess();

        accountId = memberSessionResponse.getBody().getData().getMember().getMemberId();

    }




    @Test
    public void register() {

        System.out.println("**********\tregister(Not have phone code)\t**********");

        MemberService.RegisterParam registerParam = new MemberService.RegisterParam();

        //错误验证码注册
        registerParam.setAccount(account);
        registerParam.setPassword(password);
        registerParam.setPhoneCode(416645);
        memberService.register(registerParam);

        System.out.println("**********\tregister(have phone code)\t**********");

        //正确验证码注册
        IdentifyingCodeService.SendParam sendParam = new IdentifyingCodeService.SendParam();
        sendParam.type = MemberService.VCODE_TYPE_REGISTER;
        sendParam.phone = account;
        ResponseEntity<ResponseBody<String>> rep =  identifyingCodeService.send(sendParam);

        //get send sms code for before
        IdentifyingCode code = identifyingCodeRepository.findByIdentifyingCodeId(rep.getBody().getData());

        registerParam.setPhoneCode(code.getCode());
        memberService.register(registerParam);

    }

    @Test
    public void login() {

        MemberService.LoginParam loginParam = new MemberService.LoginParam();
        loginParam.setAccount(account);
        loginParam.setPassword(password);
        memberService.login(loginParam);

    }


    @Test
    public void find(){

        register();

        MemberService.SearchParam searchParam = new MemberService.SearchParam();
        Pageable pageable = new PageRequest(0,10);
        searchParam.setWaitSetInnerDisc(false);
        memberService.search(searchParam,pageable);

    }

    @Test
    public void realName(){




        logger.info("**********\trealName(Not have Token test)\t**********");


        MemberService.RealNameParam realNameParam = new MemberService.RealNameParam();

        realNameParam.setBankCard("bankCard");
        realNameParam.setIdCard("idCard");
        realNameParam.setName("name");
        realNameParam.setPhoneNumber("phoneNumber");
        realNameParam.setIdCardBehindUrl("idCardBehind");
        realNameParam.setIdCardFrontUrl("idCardBack");

        memberService.realName(realNameParam);


        logger.info("**********\trealName(have Token test)\t**********");

        MemberService.LoginParam loginParam = new MemberService.LoginParam();
        loginParam.setPassword(password);
        loginParam.setAccount(account);
        ResponseEntity<ResponseBody<MemberSession>> rb = memberService.login(loginParam);

        if(rb.getBody().isSuccess()){
            realNameParam.setToken(rb.getBody().getData().getToken());
            memberService.realName(realNameParam);
        }







    }


    @Test
    public void setInnerDiscAccount(){


        System.out.println("**********\tsetInnerDiscAccount\t*********");

        MemberService.SetInnerDiscAccountParam setInnerDiscAccountParam = new MemberService.SetInnerDiscAccountParam();

        setInnerDiscAccountParam.setAccountId(accountId);
        setInnerDiscAccountParam.setInnerDiscAccount("innerDiscAccount");

        memberService.setInnerDiscAccount(setInnerDiscAccountParam);

    }


}