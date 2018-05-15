package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.Application;
import com.gushushu.yanao.usersys.service.IdentifyingCodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class IdentifyingCodeServiceImplTest {

    @Autowired
    private IdentifyingCodeService identifyingCodeService;

    @Test
    public void send() {

        IdentifyingCodeService.SendParam sendParam = new IdentifyingCodeService.SendParam();
        sendParam.phone="123";
        sendParam.type="register";


        identifyingCodeService.send(sendParam);

        identifyingCodeService.send(sendParam);



    }

    @Test
    public void validate() {

        IdentifyingCodeService.ValidateParam validateParam = new IdentifyingCodeService.ValidateParam();
        validateParam.code = "522817";
        validateParam.phone = "123";
        validateParam.type = "register";

        identifyingCodeService.validate(validateParam);

    }
}