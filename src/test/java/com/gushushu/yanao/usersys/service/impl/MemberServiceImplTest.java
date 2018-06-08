package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.Application;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.IdentifyingCode;
import com.gushushu.yanao.usersys.entity.MemberSession;
import com.gushushu.yanao.usersys.model.FrontMemberSession;
import com.gushushu.yanao.usersys.repository.IdentifyingCodeRepository;
import com.gushushu.yanao.usersys.repository.MemberRepository;
import com.gushushu.yanao.usersys.service.IdentifyingCodeService;
import com.gushushu.yanao.usersys.service.MemberService;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import com.gushushu.yanao.usersys.service.TransactionService;
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


    String account = "13000000002";
    String password = "888888";
    String accountId = null;
    String token = null;
    String memberId = null;

    @Before
    public void initialize(){

        MemberService.LoginParam loginParam = new MemberService.LoginParam();

        loginParam.setAccount(account);
        loginParam.setPassword(password);

        ResponseEntity<ResponseBody<FrontMemberSession>> memberSessionResponse = memberService.login(loginParam);

        assert  memberSessionResponse.getBody().isSuccess();

        accountId = memberSessionResponse.getBody().getData().getAccount();
        token = memberSessionResponse.getBody().getData().getToken();
        memberId = memberSessionResponse.getBody().getData().getMemberId();

    }



/*

    @Test
    public void frontMember(){

        memberService.getFrontMember(token);

    }
*/



    @Test
    public void realName(){




        logger.info("**********\trealName(Not have Token test)\t**********");


        MemberService.ApplyForAccountParam realNameParam = new MemberService.ApplyForAccountParam();

        realNameParam.setBankCard("bankCard");
        realNameParam.setIdCard("idCard");
        realNameParam.setName("name");
        realNameParam.setPhoneNumber("phoneNumber");
        realNameParam.setIdCardBehindUrl("idCardBehind");
        realNameParam.setIdCardFrontUrl("idCardBack");

        memberService.applyForAccount(realNameParam);


        logger.info("**********\trealName(have Token test)\t**********");

        MemberService.LoginParam loginParam = new MemberService.LoginParam();
        loginParam.setPassword(password);
        loginParam.setAccount(account);
        ResponseEntity<ResponseBody<FrontMemberSession>> rb = memberService.login(loginParam);

        if(rb.getBody().isSuccess()){
            realNameParam.setToken(rb.getBody().getData().getToken());
            memberService.applyForAccount(realNameParam);
        }







    }



    @Test
    public void update(){
   /*     MemberService.UpdateOneParam updateParam = new MemberService.UpdateOneParam();
        memberService.update(updateParam);


        updateParam.setMemberId(memberId);
        memberService.update(updateParam);

        updateParam.setOpenAccount(false);
        memberService.update(updateParam);
*/



    }


}
