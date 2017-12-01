package com.gushushu.yanao.usersys.service;

import org.springframework.http.ResponseEntity;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.ManagerInfo;
import com.gushushu.yanao.usersys.model.ManagerToken;

public interface ManagerInfoService {


    ResponseEntity<ManagerInfo> createNotExists(String account, String password);

    ResponseEntity<ManagerInfo> updatePassword(String managerId,String password,String newPassword);

    ResponseEntity<ManagerToken> login(String account,String password);

}
