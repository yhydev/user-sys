package com.gushushu.yanao.usersys.model;

public class BankInfo {

    private String bankName;
    private String bankCardNo;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return "BankInfo{" +
                "bankName='" + bankName + '\'' +
                ", bankCardNo='" + bankCardNo + '\'' +
                '}';
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }
}
