package com.gushushu.yanao.usersys.service;

import org.springframework.http.ResponseEntity;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.PhoneVCode;

public interface PhoneVCodeService {

	ResponseEntity<PhoneVCode> send(String phone, String type);

	ResponseEntity<PhoneVCode> validateCode(String phone,String type,String code);

    //ResponseBody validatePhone(String phone,String type);


}
