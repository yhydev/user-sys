package com.gushushu.yanao.usersys.model;

import java.util.Date;

public class BackMember {

    private String account;


    private String name; //姓名
    private String idCard; //身份证
    private String idCardFrontUrl; //身份证正面
    private String idCardBehindUrl; //身份证反面
    private String bankCard;//银行卡
    private String phoneNumber;//银行卡预留手机号
    private Date createDate;//注册时间
    private Date realNameTime;//实名认证时间

    public BackMember(){}

    public BackMember(String account, String name, String idCard, String idCardFrontUrl, String idCardBehindUrl, String bankCard, String phoneNumber, Date createDate, Date realNameTime) {
        this.account = account;
        this.name = name;
        this.idCard = idCard;
        this.idCardFrontUrl = idCardFrontUrl;
        this.idCardBehindUrl = idCardBehindUrl;
        this.bankCard = bankCard;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.realNameTime = realNameTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getRealNameTime() {
        return realNameTime;
    }

    public void setRealNameTime(Date realNameTime) {
        this.realNameTime = realNameTime;
    }

    @Override
    public String toString() {
        return "BackMember{" +
                "account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", idCard='" + idCard + '\'' +
                ", idCardFrontUrl='" + idCardFrontUrl + '\'' +
                ", idCardBehindUrl='" + idCardBehindUrl + '\'' +
                ", bankCard='" + bankCard + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createDate=" + createDate +
                ", realNameTime=" + realNameTime +
                '}';
    }
}
