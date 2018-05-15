package com.gushushu.yanao.usersys.entity;

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
    private Date createDate;// 创建日期
    private String type;//类型
    private String status;//状态 未启动
    private Long balance;//余额（分）
    private String email;//邮箱


    private Boolean applyForOpenAccount;//是否申请开户
    private Boolean openAccount;//是否已经开户

    private String name; //姓名
    private String idCard; //身份证
    private String idCardFrontUrl; //身份证正面
    private String idCardBehindUrl; //身份证反面
    private String bankCard;//银行卡
    private String phoneNumber;//银行卡预留手机号
    private Date realNameTime;//实名时间

    private String innerDiscAccount;//内盘账户
    private Date setInnerDiscTime;

    public Member(String memberId) {
        this.memberId = memberId;
    }
    public Member() {
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

    public Date getRealNameTime() {
        return realNameTime;
    }

    public void setRealNameTime(Date realNameTime) {
        this.realNameTime = realNameTime;
    }

    public String getInnerDiscAccount() {
        return innerDiscAccount;
    }

    public void setInnerDiscAccount(String innerDiscAccount) {
        this.innerDiscAccount = innerDiscAccount;
    }

    public Date getSetInnerDiscTime() {
        return setInnerDiscTime;
    }

    public void setSetInnerDiscTime(Date setInnerDiscTime) {
        this.setInnerDiscTime = setInnerDiscTime;
    }

    public Boolean getApplyForOpenAccount() {
        return applyForOpenAccount;
    }

    public void setApplyForOpenAccount(Boolean applyForOpenAccount) {
        this.applyForOpenAccount = applyForOpenAccount;
    }

    public Boolean getOpenAccount() {
        return openAccount != null && openAccount ;
    }

    public void setOpenAccount(Boolean openAccount) {
        this.openAccount = openAccount;
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
                ", applyForOpenAccount=" + applyForOpenAccount +
                ", openAccount=" + openAccount +
                ", name='" + name + '\'' +
                ", idCard='" + idCard + '\'' +
                ", idCardFrontUrl='" + idCardFrontUrl + '\'' +
                ", idCardBehindUrl='" + idCardBehindUrl + '\'' +
                ", bankCard='" + bankCard + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", realNameTime=" + realNameTime +
                ", innerDiscAccount='" + innerDiscAccount + '\'' +
                ", setInnerDiscTime=" + setInnerDiscTime +
                '}';
    }
}
