package com.gushushu.yanao.usersys.model;

import com.gushushu.yanao.usersys.cache.CacheObject;
import com.gushushu.yanao.usersys.entity.PhoneVCode;



public class PhoneVCodeCache extends PhoneVCode implements CacheObject  {
    public String getKey() {
        return "VerificationCodeCache_" + getPhone() + getType();
    }


}
