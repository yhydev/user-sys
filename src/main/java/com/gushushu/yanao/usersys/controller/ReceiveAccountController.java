package com.gushushu.yanao.usersys.controller;


import com.gushushu.yanao.usersys.common.annotation.HandlerRole;
import com.gushushu.yanao.usersys.entity.ReceiveAccount;
import com.gushushu.yanao.usersys.service.ReceiveAccountService;
import com.gushushu.yanao.usersys.service.impl.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receiveAccount")
public class ReceiveAccountController {

    @Autowired
    private ReceiveAccountService receiveAccountService;

    @GetMapping
    public ResponseEntity getAll(){
        return receiveAccountService.findAll();
    }


    @PostMapping
    @HandlerRole({MemberServiceImpl.MANAGER_TYPE})
    public ResponseEntity saveOrUpdate(@Validated  ReceiveAccount receiveAccount){
        return receiveAccountService.saveOrUpdate(receiveAccount);
    }



}
