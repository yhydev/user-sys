package com.gushushu.yanao.usersys.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ReceiveAccount {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(strategy = "uuid",name = "uuid")
    private String receiveAccountId;

    @NotEmpty(message = "户主姓名不能为空")
    private String username;
    @NotEmpty(message = "银行名称不能为空")
    private String bankName;
    @NotEmpty(message = "银行卡号不能为空")
    private String bankNo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ReceiveAccount{" +
                "receiveAccountId='" + receiveAccountId + '\'' +
                ", username='" + username + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankNo='" + bankNo + '\'' +
                '}';
    }

    public String getReceiveAccountId() {
        return receiveAccountId;
    }

    public void setReceiveAccountId(String receiveAccountId) {
        this.receiveAccountId = receiveAccountId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }
}
