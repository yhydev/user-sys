package com.gushushu.yanao.usersys.service;

import com.gushushu.yanao.usersys.common.ResponseBody;

import java.awt.image.BufferedImage;

import org.springframework.http.ResponseEntity;

public interface ImageVCodeService {

    ResponseEntity<BufferedImage> create(String session);


    ResponseEntity<BufferedImage> validate(String session,String imageCode);





}
