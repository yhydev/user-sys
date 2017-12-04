package com.gushushu.yanao.usersys.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.gushushu.yanao.usersys.entity.UserInfo;
import com.gushushu.yanao.usersys.model.BankInfo;
import com.gushushu.yanao.usersys.model.IdInfo;
import com.gushushu.yanao.usersys.model.UserToken;

public interface UserInfoService {



    ResponseEntity<UserInfo> register(String account,String phoneCode,String password);

    ResponseEntity<UserToken> login(String account,String password);

    ResponseEntity<UserInfo> findPassword(String account,String phoneCode,String password);

    ResponseEntity<UserInfo> updatePassword(String userId,String password,String newPassword);

    ResponseEntity<UserInfo> updateEmail(String userId, String password,String email);

    ResponseEntity<UserInfo> update(String userId,BankInfo bankInfo, IdInfo idInfo);

    ResponseEntity<UserInfo> updateMoney(String userId,Long money);

    ResponseEntity<UserInfo> updateOuterDiscAccount(String userId,String outerDiscAccount);

    ResponseEntity<Page> find(String proxyUserId,int page,int size);

}
