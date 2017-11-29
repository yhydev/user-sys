package com.gushushu.yanao.usersys.common;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecretEncode {

	public static String md5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BASE64Encoder base64en = new BASE64Encoder();
		// 加密后的字符串
		String newstr = null;
		try {
			newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newstr;
	}

}
