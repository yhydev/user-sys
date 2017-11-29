package com.gushushu.yanao.usersys.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
public class UserInfo {


    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String userId;
    private String account;//账户 (手机号)
    private String password;//密码
    private String phone;//手机号

    private Date createDate;// 创建日期
    private String type;//类型 未启用
    private String status;//状态 未启动
    private String id;//身份证
    private String name;// 姓名
    private Long balance;//余额（分）
    private String email;//邮箱
    private String bankCardNo;//银行卡
    private String bankName;//银行卡名称
    private String outerDiscAccount;//外盘账户

    public String getOuterDiscAccount() {
        return outerDiscAccount;
    }



    public void setOuterDiscAccount(String outerDiscAccount) {
        this.outerDiscAccount = outerDiscAccount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", createDate=" + createDate +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", email='" + email + '\'' +
                ", bankCardNo='" + bankCardNo + '\'' +
                ", bankName='" + bankName + '\'' +
                ", outerDiscAccount='" + outerDiscAccount + '\'' +
                '}';
    }

}
