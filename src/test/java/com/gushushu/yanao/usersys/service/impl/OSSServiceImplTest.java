package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.Application;
import com.gushushu.yanao.usersys.service.OSSService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class OSSServiceImplTest {

    @Autowired
    private OSSService ossService;

    @Test
    public void upload() throws FileNotFoundException {

        InputStream inputStream = new FileInputStream("C:\\Windows\\system.ini");
        ossService.upload(inputStream,"system.ini");

    }
}