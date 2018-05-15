package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.service.ValidateService;
import org.junit.Test;

import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class ValidateServiceImplTest {

    @Test
    public void bankCard() throws URISyntaxException {


        ValidateService validateService = new ValidateServiceImpl();

        validateService.bankCard("111");
        validateService.bankCard("6212261702013626387");


    }
}