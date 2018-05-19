package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.Application;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.ReceiveAccount;
import com.gushushu.yanao.usersys.service.ReceiveAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ReceiveAccountServiceImplTest {

    @Autowired
    private ReceiveAccountService receiveAccountService;

    @Test
    public void saveOrUpdate() {

        ReceiveAccount receiveAccount = new ReceiveAccount();


        receiveAccount.setBankNo("6212261702013626387");

        //第一次成功，其他失败
        ResponseEntity<ResponseBody<String>>  resp  = receiveAccountService.saveOrUpdate(receiveAccount);

        //失败
        receiveAccountService.saveOrUpdate(receiveAccount);

        //成功
        receiveAccount.setReceiveAccountId(resp.getBody().getData());
        receiveAccountService.saveOrUpdate(receiveAccount);









    }

    @Test
    public void findAll() {
    }
}