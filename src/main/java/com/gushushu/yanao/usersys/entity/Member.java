package com.gushushu.yanao.usersys.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gushushu.yanao.usersys.serialize.DateSerialize;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Member {


    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String memberId;
    private String account;//账户 (手机号)
    private String password;//密码
    @JsonSerialize(using = DateSerialize.class)
    private Date createDate;// 创建日期
    private String type;//类型
    private String status;//状态 未启动
    private Long balance;//余额（分）
    private String email;//邮箱


    private String openAccountStatus;//开户状态

    private String name; //姓名
    private String idCard; //身份证
    private String idCardFrontUrl; //身份证正面
    private String idCardBehindUrl; //身份证反面
    private String bankCard;//银行卡
    private String phoneNumber;//银行卡预留手机号
    private Date applyForOpenAccountDate;//实名时间

    private String innerDiscAccount;//内盘账户
    private Date setInnerDiscDate;//设置内盘账户日期

    public Member() {
    }

    public Member(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId='" + memberId + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", createDate=" + createDate +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", balance=" + balance +
                ", email='" + email + '\'' +
                ", openAccountStatus=" + openAccountStatus +
                ", name='" + name + '\'' +
                ", idCard='" + idCard + '\'' +
                ", idCardFrontUrl='" + idCardFrontUrl + '\'' +
                ", idCardBehindUrl='" + idCardBehindUrl + '\'' +
                ", bankCard='" + bankCard + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", applyForOpenAccountDate=" + applyForOpenAccountDate +
                ", innerDiscAccount='" + innerDiscAccount + '\'' +
                ", setInnerDiscDate=" + setInnerDiscDate +
                '}';
    }

    public String getOpenAccountStatus() {
        return openAccountStatus;
    }

    public void setOpenAccountStatus(String openAccountStatus) {
        this.openAccountStatus = openAccountStatus;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

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
        this.password = password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getApplyForOpenAccountDate() {
        return applyForOpenAccountDate;
    }

    public void setApplyForOpenAccountDate(Date applyForOpenAccountDate) {
        this.applyForOpenAccountDate = applyForOpenAccountDate;
    }

    public String getInnerDiscAccount() {
        return innerDiscAccount;
    }

    public void setInnerDiscAccount(String innerDiscAccount) {
        this.innerDiscAccount = innerDiscAccount;
    }

    public Date getSetInnerDiscDate() {
        return setInnerDiscDate;
    }

    public void setSetInnerDiscDate(Date setInnerDiscDate) {
        this.setInnerDiscDate = setInnerDiscDate;
    }
}
