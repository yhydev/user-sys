package com.gushushu.yanao.usersys.service;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.model.BankInfo;
import com.gushushu.yanao.usersys.model.IdInfo;

public interface UserInfoService {



    ResponseBody register(String account,String phoneCode,String password);

    ResponseBody login(String account,String password);

    ResponseBody findPassword(String account,String phoneCode,String password);

    ResponseBody updatePassword(String userId,String password,String newPassword);

    ResponseBody updateEmail(String userId, String password,String email);

    ResponseBody update(String userId,BankInfo bankInfo, IdInfo idInfo);

    ResponseBody updateMoney(String userId,Long money);

    ResponseBody updateOuterDiscAccount(String userId,String outerDiscAccount);

}
