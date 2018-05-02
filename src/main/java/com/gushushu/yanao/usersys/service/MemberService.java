package com.gushushu.yanao.usersys.service;

import com.gushushu.yanao.usersys.entity.Member;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.http.ResponseEntity;

import com.gushushu.yanao.usersys.model.BankInfo;
import com.gushushu.yanao.usersys.model.IdInfo;

public interface MemberService {



    ResponseEntity<Member> register(String account, String phoneCode, String password);

//    ResponseEntity<UserToken> login(String account,String password);

    ResponseEntity<Member> findPassword(String account,String phoneCode,String password);

    ResponseEntity<Member> updatePassword(String userId,String password,String newPassword);

    ResponseEntity<Member> updateEmail(String userId, String password,String email);

    ResponseEntity<Member> update(String userId,BankInfo bankInfo, IdInfo idInfo);

    ResponseEntity<Member> updateMoney(String userId,Long money);

    ResponseEntity<Member> updateOuterDiscAccount(String userId,String outerDiscAccount);



    public static class RegisterParam{
        private String account;
        private String phoneCode;
        private String password;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPhoneCode() {
            return phoneCode;
        }

        public void setPhoneCode(String phoneCode) {
            this.phoneCode = MD5Encoder.encode(phoneCode.getBytes());
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "RegisterParam{" +
                    "account='" + account + '\'' +
                    ", phoneCode='" + phoneCode + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }


}
