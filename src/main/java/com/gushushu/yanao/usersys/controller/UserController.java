package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.config.AppConstant;
import com.gushushu.yanao.usersys.entity.UserInfo;
import com.gushushu.yanao.usersys.model.UserToken;
import com.gushushu.yanao.usersys.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController implements AppConstant {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("register")
    public ResponseEntity<UserInfo> register(String account,String password,String phoneCode){
        return userInfoService.register(account,phoneCode,password);
    }

    @PostMapping("login")
    public ResponseEntity<UserToken> login(String account,String password){
        return userInfoService.login(account,password);
    }

    @PostMapping("updatePassword")
    public ResponseEntity<UserInfo> updatePassword(String userId,String password,String newPassword){
        return userInfoService.updatePassword(userId, password, newPassword);
    }

    @PostMapping("updateEmail")
    public ResponseEntity<UserInfo> updateEmail(String userId,String password,String email){
        return userInfoService.updateEmail(userId, password, email);
    }


    @PostMapping("findPassword")
    public ResponseEntity<UserInfo> findPassword(String account,String password,String phoneCode){
        return userInfoService.findPassword(account, phoneCode, password);
    }
    @PostMapping("updateOuterDiscAccount")
    public ResponseEntity<UserInfo> updateOuterDiscAccount(String userId,String outerDiscAccount){
        return userInfoService.updateOuterDiscAccount(userId,outerDiscAccount);
    }




}
