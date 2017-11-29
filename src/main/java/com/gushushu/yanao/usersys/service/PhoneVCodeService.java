package com.gushushu.yanao.usersys.service;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.PhoneVCode;

public interface PhoneVCodeService {

    ResponseBody<PhoneVCode> send(String phone, String type);

    ResponseBody validateCode(String phone,String type,String code);

    //ResponseBody validatePhone(String phone,String type);


}
