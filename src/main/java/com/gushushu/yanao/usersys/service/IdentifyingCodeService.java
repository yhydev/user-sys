package com.gushushu.yanao.usersys.service;


import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.IdentifyingCode;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import javax.validation.constraints.Size;
import java.util.Random;

public interface IdentifyingCodeService {


	public final static Random random = new Random();

	public static final String UNVERIFIED_STATUS = "Unverified";

	public static final String VERIFIED_STATUS = "verified";

	/**
	 * it send sms code
	 * @param sendParam
	 * @return if isSuccess is true of responseBody, data of it is  identifyingCodeId
	 */
	ResponseEntity<ResponseBody<String>> send(SendParam sendParam);

	ResponseEntity<ResponseBody<IdentifyingCode>> validate(ValidateParam validateParam);



	public static class SendParam{


		@Size(min = 11,max = 11,message = "手机号格式不正确")
		@NotEmpty(message = "手机号不能为空")
		public String phone;

		@NotEmpty(message = "验证码类型不能为空")
		public String type;



		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

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
		public String code;

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
