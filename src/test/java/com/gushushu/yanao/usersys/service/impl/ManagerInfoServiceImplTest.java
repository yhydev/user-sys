package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.Application;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.ManagerInfo;
import com.gushushu.yanao.usersys.model.ManagerToken;
import com.gushushu.yanao.usersys.service.ManagerInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ManagerInfoServiceImplTest {

    @Autowired
    ManagerInfoService managerInfoService;


    @Test
    public void createNotExists() throws Exception {



    }

    @Test
    public void updatePassword() throws Exception {

        String account = "superMan";
        String password = "123456";//原密码
        String newPassword = "12345678";//新密码



        //如果不存在此账户则创建
       ResponseBody<ManagerInfo> managerInfoResponseBody =  managerInfoService.createNotExists(account,password);
       ResponseBody<ManagerToken> managerTokenResponseBody = null;

       if(managerInfoResponseBody.getSuccess()){

           //登陆
           managerTokenResponseBody = managerInfoService.login(account,newPassword);

           //修改密码为 新密码
           managerInfoService.updatePassword(managerInfoResponseBody.getData().getManagerId(),password,newPassword);

           //修改密码为 当前密码
           managerInfoService.updatePassword(managerInfoResponseBody.getData().getManagerId(),newPassword,newPassword);


           //修改密码为 原密码
           managerInfoService.updatePassword(managerInfoResponseBody.getData().getManagerId(),newPassword,password);


       }








    }

}