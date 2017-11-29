package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.Application;
import com.gushushu.yanao.usersys.service.PhoneVCodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class VerificationCodeServiceImplTest {

    @Autowired
    private PhoneVCodeService verificationCodeService;

    @Test
    public void send() throws Exception {

        verificationCodeService.send("13000000000","register");
        verificationCodeService.send("13000000000","findPassword");

    }

    @Test
    public void validateCode() throws Exception {

        verificationCodeService.validateCode("13000000000","register","53315");
    }

    @Test
    public void validatePhone() throws Exception {
      //  verificationCodeService.validatePhone("13000000000","register");

    }

}
