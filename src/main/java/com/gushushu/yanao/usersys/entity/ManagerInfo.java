package com.gushushu.yanao.usersys.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ManagerInfo {

    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String managerId;
    private String account;
    private String password;

    @Override
    public String toString() {
        return "ManagerInfo{" +
                "managerId='" + managerId + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
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
}
