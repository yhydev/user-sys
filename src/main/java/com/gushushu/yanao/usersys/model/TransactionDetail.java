package com.gushushu.yanao.usersys.model;

public class TransactionDetail {

    //用户信息
    private String name;
    private String account;
    private String memberBankCard;
    private String innerDiscAccount;

    //交易信息
    private String type;
    private String status;
    private Long money;//交易金额


    private String payAccount;
    private String receiveBankNo;//收款银行号
    private String receiveBankName;//收款银行
    private String receiveUsername;

    @Override
    public String toString() {
        return "TransactionDetail{" +
                "name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", memberBankCard='" + memberBankCard + '\'' +
                ", innerDiscAccount='" + innerDiscAccount + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", money=" + money +
                ", payAccount='" + payAccount + '\'' +
                ", receiveBankNo='" + receiveBankNo + '\'' +
                ", receiveBankName='" + receiveBankName + '\'' +
                ", receiveUsername='" + receiveUsername + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMemberBankCard() {
        return memberBankCard;
    }

    public void setMemberBankCard(String memberBankCard) {
        this.memberBankCard = memberBankCard;
    }

    public String getInnerDiscAccount() {
        return innerDiscAccount;
    }

    public void setInnerDiscAccount(String innerDiscAccount) {
        this.innerDiscAccount = innerDiscAccount;
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

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getReceiveBankNo() {
        return receiveBankNo;
    }

    public void setReceiveBankNo(String receiveBankNo) {
        this.receiveBankNo = receiveBankNo;
    }

    public String getReceiveBankName() {
        return receiveBankName;
    }

    public void setReceiveBankName(String receiveBankName) {
        this.receiveBankName = receiveBankName;
    }

    public String getReceiveUsername() {
        return receiveUsername;
    }

    public void setReceiveUsername(String receiveUsername) {
        this.receiveUsername = receiveUsername;
    }
}
