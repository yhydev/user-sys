package com.gushushu.yanao.usersys.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gushushu.yanao.usersys.serialize.DateSerialize;

import java.util.Date;

public class BackTransaction {

    private String status;
    private String type;
    private Long money;
    @JsonSerialize(using = DateSerialize.class)
    private Date createDate;
    @JsonSerialize(using = DateSerialize.class)
    private Date updateDate;
    private String answer;
    private String detailId;
    private String account;
    private String transactionId;

    @Override
    public String toString() {
        return "BackTransaction{" +
                "status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", money=" + money +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", answer='" + answer + '\'' +
                ", detailId='" + detailId + '\'' +
                ", account='" + account + '\'' +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
