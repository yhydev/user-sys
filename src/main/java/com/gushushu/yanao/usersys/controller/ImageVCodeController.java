package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.service.ImageVCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

@RestController
@RequestMapping("/imageVCode")
public class ImageVCodeController {

    @Autowired
    private ImageVCodeService imageVCodeService;

    @GetMapping
    public void get(HttpServletRequest request, HttpServletResponse response){


        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ResponseBody<BufferedImage> rb = imageVCodeService.create(request.getSession().getId());

        if(rb.getSuccess()){
            try {
                OutputStream os = response.getOutputStream();
                ImageIO.write(rb.getData(),"jpg",os);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

}
