package com.gushushu.yanao.usersys.service;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.ManagerInfo;

public interface ManagerInfoService {


    ResponseBody<ManagerInfo> createNotExists(String account, String password);

    ResponseBody updatePassword(String managerId,String password,String newPassword);

    ResponseBody login(String account,String password);

}
