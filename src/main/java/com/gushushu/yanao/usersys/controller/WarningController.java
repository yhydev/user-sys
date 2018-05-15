package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WarningController {

    @RequestMapping("/warning")
    public ResponseEntity warning(String message){
        return ResponseEntityBuilder.failed(message);
    }



}
