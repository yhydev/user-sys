package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.service.ImageVCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

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
        ResponseEntity<BufferedImage> re = imageVCodeService.create(request.getSession().getId());

        if(re.getStatusCode() == HttpStatus.OK){
            try {
                OutputStream os = response.getOutputStream();
                ImageIO.write(re.getBody(),"jpg",os);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

}
