package com.gushushu.yanao.usersys.common;

import org.apache.tomcat.util.buf.HexUtils;

public class RandomUtils {

    public static String generate(){
        String str = System.currentTimeMillis() + String.valueOf(Math.random());
        String ret = HexUtils.toHexString(str.getBytes());
        return ret;
    }


}
