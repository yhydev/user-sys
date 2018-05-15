package com.gushushu.yanao.usersys.service;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.SecretEncode;
import com.gushushu.yanao.usersys.entity.MemberSession;
import com.gushushu.yanao.usersys.model.BackMember;
import com.gushushu.yanao.usersys.model.FrontMember;
import com.gushushu.yanao.usersys.model.FrontMemberSession;
import org.hibernate.validator.constraints.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

public interface MemberService {

    //注册验证码类型
    public final static String VCODE_TYPE_REGISTER = "register";

    //找回密码验证码类型
    public final static String VCODE_TYPE_VERIFICATION_CODE = "findPassword";

    ResponseEntity<ResponseBody<FrontMember>> getFrontMember(String token);

    ResponseEntity<ResponseBody<String>> setInnerDiscAccount(SetInnerDiscAccountParam setInnerDiscAccountParam);

    ResponseEntity<ResponseBody<FrontMemberSession>> register(RegisterParam registerParam);

    ResponseEntity<ResponseBody<FrontMemberSession>> login(LoginParam loginParam);

    ResponseEntity<ResponseBody<Page<BackMember>>> search(SearchParam param, Pageable pageable);

    ResponseEntity<ResponseBody> realName(RealNameParam param);



    /*

    ResponseEntity<Member> findPassword(String account,String phoneCode,String password);

    ResponseEntity<Member> updatePassword(String userId,String password,String newPassword);

    ResponseEntity<Member> updateEmail(String userId, String password,String email);


    ResponseEntity<Member> updateMoney(String userId,Long money);

    ResponseEntity<Member> updateOuterDiscAccount(String userId,String outerDiscAccount);
*/


    public static class SetInnerDiscAccountParam{
        private String accountId;
        private String innerDiscAccount;

        @Override
        public String toString() {
            return "SetInnerDiscAccountParam{" +
                    "accountId='" + accountId + '\'' +
                    ", innerDiscAccount='" + innerDiscAccount + '\'' +
                    '}';
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getInnerDiscAccount() {
            return innerDiscAccount;
        }

        public void setInnerDiscAccount(String innerDiscAccount) {
            this.innerDiscAccount = innerDiscAccount;
        }
    }

    public static class RealNameParam{
        @NotEmpty(message = "token 不能为空")
        @Length(message = "token 有误")
        private String token;

        @NotEmpty(message = "姓名不能为空")
        private String name; //姓名

        @Length(min = 15,max = 18)
        @NotEmpty(message = "身份证不能为空")
        private String idCard; //身份证

        @URL(message = "身份证正面有误")
        @NotEmpty(message = "身份证正面不能为空")
        private String idCardFrontUrl; //身份证正面

        @URL(message = "身份证反面有误")
        @NotEmpty(message = "身份证反面不能为空")
        private String idCardBehindUrl; //身份证反面

        @Length(min = 16,max = 19,message = "银行卡有误")
        @NotEmpty(message = "银行卡不能为空")
        private String bankCard;//银行卡

        @Length(min = 11,max = 11,message = "银行卡预留号码有误")
        @NotEmpty(message = "银行卡预留号码不能为空")
        private String phoneNumber;//银行卡预留手机号

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getIdCardFrontUrl() {
            return idCardFrontUrl;
        }

        public void setIdCardFrontUrl(String idCardFrontUrl) {
            this.idCardFrontUrl = idCardFrontUrl;
        }

        public String getIdCardBehindUrl() {
            return idCardBehindUrl;
        }

        public void setIdCardBehindUrl(String idCardBehindUrl) {
            this.idCardBehindUrl = idCardBehindUrl;
        }

        public String getBankCard() {
            return bankCard;
        }

        public void setBankCard(String bankCard) {
            this.bankCard = bankCard;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @Override
        public String toString() {
            return "RealNameParam{" +
                    "token='" + token + '\'' +
                    ", name='" + name + '\'' +
                    ", idCard='" + idCard + '\'' +
                    ", idCardFrontUrl='" + idCardFrontUrl + '\'' +
                    ", idCardBehindUrl='" + idCardBehindUrl + '\'' +
                    ", bankCard='" + bankCard + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    '}';
        }
    }

    public static class SearchParam {
        private Boolean waitSetInnerDisc;

        public Boolean getWaitSetInnerDisc() {
            return waitSetInnerDisc;
        }

        public void setWaitSetInnerDisc(Boolean waitSetInnerDisc) {
            this.waitSetInnerDisc = waitSetInnerDisc;
        }

        @Override
        public String toString() {
            return "SearchParam{" +
                    "waitSetInnerDisc=" + waitSetInnerDisc +
                    '}';
        }
    }

    public static class RegisterParam extends LoginParam{

        @NotBlank(message = "验证码不能为空")
        private String phoneCode;

        public String getPhoneCode() {
            return phoneCode;
        }

        public void setPhoneCode(String phoneCode) {
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

        @NotEmpty(message = "手机号不能为空")
        @Length(min = 11,max = 11,message = "手机号格式不正确")
        private String account;

        @NotEmpty(message = "密码不能为空")
        @Length(min = 5,max = 18,message = "密码格式为 6-18 个字符")
        private String password;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return SecretEncode.md5(password);
        }

        public void setPassword(String password) {
            this.password = password;
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
