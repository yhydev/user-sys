package com.gushushu.yanao.usersys.model;

import java.util.Date;

public class BackMember {

    private String memberId;
    private String account;//账户 (手机号)
    private Date createDate;// 创建日期

    private Boolean applyForOpenAccount;//是否申请开户
    private Boolean openAccount;//是否已经开户

    private String name; //姓名
    private String idCard; //身份证
    private String idCardFrontUrl; //身份证正面
    private String idCardBehindUrl; //身份证反面
    private String bankCard;//银行卡
    private String phoneNumber;//银行卡预留手机号
    private Date applyForOpenAccountDate;//实名时间

    private String innerDiscAccount;//内盘账户
    private Date setInnerDiscDate;//设置内盘账户日期


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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getApplyForOpenAccount() {
        return applyForOpenAccount;
    }

    public void setApplyForOpenAccount(Boolean applyForOpenAccount) {
        this.applyForOpenAccount = applyForOpenAccount;
    }

    public Boolean getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(Boolean openAccount) {
        this.openAccount = openAccount;
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
