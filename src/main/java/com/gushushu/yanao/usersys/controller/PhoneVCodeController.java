package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.config.AppConstant;
import com.gushushu.yanao.usersys.service.ImageVCodeService;
import com.gushushu.yanao.usersys.service.PhoneVCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/phoneVCode")
public class PhoneVCodeController implements AppConstant {

    @Autowired
    private PhoneVCodeService verificationCodeService;

    @Autowired
    private ImageVCodeService imageVCodeService;

    @PostMapping(params = {STRING_IMAGE_CODE})
    public ResponseBody send(String phone, String type, String imageCode, HttpServletRequest request){
        ResponseBody rb = imageVCodeService.validate(request.getSession().getId(),imageCode);
        if(rb.getSuccess()){
            rb = verificationCodeService.send(phone,type).success(null);
        }
        return rb;
    }







}
