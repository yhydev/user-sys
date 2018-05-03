package com.gushushu.yanao.usersys.service;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.entity.MemberSession;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.http.ResponseEntity;

public interface MemberService {

    //注册验证码类型
    public final static String VCODE_TYPE_REGISTER = "register";

    //找回密码验证码类型
    public final static String VCODE_TYPE_VERIFICATION_CODE = "findPassword";


    ResponseEntity<ResponseBody<MemberSession>> register(RegisterParam registerParam);

    ResponseEntity<ResponseBody<MemberSession>> login(LoginParam loginParam);
/*

    ResponseEntity<Member> findPassword(String account,String phoneCode,String password);

    ResponseEntity<Member> updatePassword(String userId,String password,String newPassword);

    ResponseEntity<Member> updateEmail(String userId, String password,String email);


    ResponseEntity<Member> updateMoney(String userId,Long money);

    ResponseEntity<Member> updateOuterDiscAccount(String userId,String outerDiscAccount);
*/



    public static class RegisterParam extends LoginParam{
        private Integer phoneCode;


        public Integer getPhoneCode() {
            return phoneCode;
        }

        public void setPhoneCode(Integer phoneCode) {
            this.phoneCode = phoneCode;
        }

        @Override
        public String toString() {
            return "RegisterParam{" +
                    "phoneCode='" + phoneCode + '\'' +
                    "} " + super.toString();
        }
    }


    public static class LoginParam{
        private String account;
        private String password;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = MD5Encoder.encode(password.getBytes());
        }

        @Override
        public String toString() {
            return "LoginParam{" +
                    "account='" + account + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }


}
