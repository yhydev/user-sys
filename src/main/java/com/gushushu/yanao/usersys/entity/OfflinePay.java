package com.gushushu.yanao.usersys.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OfflinePay{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    private String offLineId;//线下支付id
    private String payAccount;//交易账户
    private String receiveAccount;//接收账户


    @Override
    public String toString() {
        return "OffLinePay{" +
                "offLineId='" + offLineId + '\'' +
                ", payAccount='" + payAccount + '\'' +
                ", receiveAccount='" + receiveAccount + '\'' +
                '}';
    }

    public String getOffLineId() {
        return offLineId;
    }

    public void setOffLineId(String offLineId) {
        this.offLineId = offLineId;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getReceiveAccount() {
        return receiveAccount;
    }

    public void setReceiveAccount(String receiveAccount) {
        this.receiveAccount = receiveAccount;
    }
}
