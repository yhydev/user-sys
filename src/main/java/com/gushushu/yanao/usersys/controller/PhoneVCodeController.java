package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.config.AppConstant;
import com.gushushu.yanao.usersys.entity.PhoneVCode;
import com.gushushu.yanao.usersys.service.ImageVCodeService;
import com.gushushu.yanao.usersys.service.PhoneVCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/phoneVCode")
public class PhoneVCodeController implements AppConstant {

    @Autowired
    private PhoneVCodeService verificationCodeService;

    @Autowired
    private ImageVCodeService imageVCodeService;

    @PostMapping(params = {STRING_IMAGE_CODE})
    public ResponseEntity<PhoneVCode> send(String phone, String type, String imageCode, HttpServletRequest request){
    	ResponseEntity<BufferedImage> re = imageVCodeService.validate(request.getSession().getId(),imageCode);
        if(re.getStatusCode() == HttpStatus.OK){
            return verificationCodeService.send(phone,type);
        }else{
        	return new ResponseEntityBuilder<PhoneVCode>().builder(HttpStatus.BAD_REQUEST, ERROR, "验证码错误");
        }
    }







}
