package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.service.OSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class FileUploadController {


    @Autowired
    private OSSService ossService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity upload(MultipartFile file) throws IOException {

        ResponseEntity rb = null;

        if(!file.isEmpty()){
            rb = ossService.upload(file.getInputStream(),file.getOriginalFilename());
        }

        return rb;
    }


}
