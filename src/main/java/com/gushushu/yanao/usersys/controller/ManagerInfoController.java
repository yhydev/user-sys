package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.service.ManagerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerInfoController {

    @Autowired
    private ManagerInfoService managerInfoService;

    @PostMapping
    public ResponseBody login(String account,String password){

        return managerInfoService.login(account,password);

        //return "";
    }


}
