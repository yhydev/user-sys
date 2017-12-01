package com.gushushu.yanao.usersys.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    @RequestMapping("/security")
    public ResponseEntity<String> error(String message){
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

}
