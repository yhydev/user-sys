package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.Application;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.config.AppConstant;
import com.gushushu.yanao.usersys.model.FrontMemberSession;
import com.gushushu.yanao.usersys.service.MemberService;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import com.gushushu.yanao.usersys.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionServiceImplTest implements AppConstant{

    @Autowired
    private TransactionService transactionService;

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
    public void underLinePay(){

        TransactionService.UnderLinePayParam underLinePayParam = new TransactionService.UnderLinePayParam();

        underLinePayParam.setMoney(100L);
        underLinePayParam.setToken(token);
        underLinePayParam.setReceiveAccount("6212261702013626387");
        underLinePayParam.setPayAccount("6212261702013626387");
        transactionService.underLinePay(underLinePayParam);



        underLinePayParam.setReceiveAccount("2222");
        underLinePayParam.setPayAccount("1111");
        transactionService.underLinePay(underLinePayParam);


    }


}
