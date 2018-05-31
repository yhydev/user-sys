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
    private String offlinePayId;//线下支付id
    private String payAccount;//支付账号
    private String receiveBankNo;//接收账户
    private String receiveUserName;//收款人名称
    private String receiveBankName;//收款支行名称

    @Override
    public String toString() {
        return "OfflinePay{" +
                "offlinePayId='" + offlinePayId + '\'' +
                ", payAccount='" + payAccount + '\'' +
                ", receiveBankNo='" + receiveBankNo + '\'' +
                ", receiveUserName='" + receiveUserName + '\'' +
                ", receiveBankName='" + receiveBankName + '\'' +
                '}';
    }

    public String getOfflinePayId() {
        return offlinePayId;
    }

    public void setOfflinePayId(String offlinePayId) {
        this.offlinePayId = offlinePayId;
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

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getReceiveBankName() {
        return receiveBankName;
    }

    public void setReceiveBankName(String receiveBankName) {
        this.receiveBankName = receiveBankName;
    }
}
