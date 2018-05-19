package com.gushushu.yanao.usersys.service;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.ReceiveAccount;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReceiveAccountService {



    public static final String ENABLE_STATUS = "enable";
    public static final String DISABLE_STATUS = "disable";


    ResponseEntity<ResponseBody<String>> saveOrUpdate(ReceiveAccount receiveAccount);

    ResponseEntity<ResponseBody<List<ReceiveAccount>>> findAll();






}
