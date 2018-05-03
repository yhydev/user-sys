package com.gushushu.yanao.usersys.service;


import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.IdentifyingCode;
import org.springframework.http.ResponseEntity;

import java.util.Random;

public interface IdentifyingCodeService {


	public final static Random random = new Random();

	public static final String UNVERIFIED_STATUS = "Unverified";

	public static final String VERIFIED_STATUS = "verified";

	ResponseEntity send(SendParam sendParam);

	ResponseEntity<ResponseBody<IdentifyingCode>> validate(ValidateParam validateParam);



	public static class SendParam{
		public String phone;
		public String type;

		@Override
		public String toString() {
			return "SendParam{" +
					"phone='" + phone + '\'' +
					", type='" + type + '\'' +
					'}';
		}
	}

	public static class ValidateParam{
		public String phone;
		public String type;
		public Integer code;

		@Override
		public String toString() {
			return "ValidateParam{" +
					"phone='" + phone + '\'' +
					", type='" + type + '\'' +
					", code='" + code + '\'' +
					'}';
		}
	}




}
