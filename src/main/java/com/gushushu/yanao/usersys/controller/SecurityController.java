package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.common.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    @RequestMapping("/security")
    public ResponseBody error(String message){
        return new ResponseBody().error(message);
    }

}
