package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.Application;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.PhoneVCode;
import com.gushushu.yanao.usersys.model.BankInfo;
import com.gushushu.yanao.usersys.model.IdInfo;
import com.gushushu.yanao.usersys.model.UserToken;
import com.gushushu.yanao.usersys.service.UserInfoService;
import com.gushushu.yanao.usersys.service.PhoneVCodeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserInfoServiceImplTest {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PhoneVCodeService verificationCodeService;



    private String userId;
    private String account = "13000000001";
    private String password = "123456";

    @Before
    public  void before(){
        //登陆
        ResponseBody<UserToken> userRB = userInfoService.login(account,password);
        if(userRB.getSuccess()){
            userId = userRB.getData().getUserId();
        }else{


            //发送验证码 ，注册
            ResponseBody<PhoneVCode> codeRb = verificationCodeService.send(account,UserInfoServiceImpl.REGISTER_VERIFICATION_CODE);
            ResponseBody regRB = userInfoService.register(account,codeRb.getData().getCode(),password);

            //是否注册成功
            if(!regRB.getSuccess()){
                codeRb = verificationCodeService.send(account,UserInfoServiceImpl.FIND_PASSWORD_VERIFICATION_CODE);
                userInfoService.findPassword(account,codeRb.getData().getCode(),password);
            }

            //登陆
             userRB = userInfoService.login(account,password);
            userId = userRB.getData().getUserId();



        }


    }


    @Test
    public void updateMoney(){

        userInfoService.updateMoney(userId,10000L);

        userInfoService.updateMoney(userId,-100001L);

        userInfoService.updateMoney(userId,-10000L);

    }









    @Test
    public void register() throws Exception {
        ResponseBody<PhoneVCode> rb = null;
        rb = verificationCodeService.send("13000000000","register");
        userInfoService.register("13000000000",rb.getData().getCode(),"123456");

         rb = verificationCodeService.send("13000000001","register");
        userInfoService.register("13000000001",rb.getData().getCode(),"123456");

    }

    @Test
    public void login() throws Exception {
        userInfoService.login("13000000000","123456");
    }

    @Test
    public void updatePassword() throws Exception {

       ResponseBody<UserToken> rb =  userInfoService.login("13000000001","123456");

        userInfoService.updatePassword(rb.getData().getUserId(),"12345678","123456");

    }

    @Test
    public void updateEmail() throws Exception {


        //boolean v = Pattern.matches(".+@.+\\..+","12313131@qq.com");
        //  System.out.println(v);
        userInfoService.updateEmail(userId,password,"812783704@qq.com");
/*
        userInfoService.updateEmail(rb.getData().getUserId(),"12345678","12345678");

        userInfoService.updateEmail(rb.getData().getUserId(),"12345678","813@a.com");*/


    }

    @Test
    public void update(){
        BankInfo bankInfo = new BankInfo();
        IdInfo idInfo = new IdInfo();

        bankInfo.setBankCardNo("bankCardNo");
        bankInfo.setBankName("bankName");

        idInfo.setId("id");
        idInfo.setName("name");

        userInfoService.update(userId,bankInfo,idInfo);
    }

}