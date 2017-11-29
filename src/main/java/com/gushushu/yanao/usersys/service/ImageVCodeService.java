package com.gushushu.yanao.usersys.service;

import com.gushushu.yanao.usersys.common.ResponseBody;

import java.awt.image.BufferedImage;

public interface ImageVCodeService {

    ResponseBody<BufferedImage> create(String session);


    ResponseBody validate(String session,String imageCode);





}
