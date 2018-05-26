package com.gushushu.yanao.usersys.model;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * front model ,it show to member self
 *
 */
public class FrontMember {

    private Boolean openAccount;
    private Boolean applyForOpenAccount;
    private String innerDiscAccount;
    private String account;
    private String type;


    public FrontMember(Boolean openAccount, Boolean applyForOpenAccount, String innerDiscAccount,String account,String type) {
        this.openAccount = openAccount;
        this.applyForOpenAccount = applyForOpenAccount;
        this.innerDiscAccount = innerDiscAccount;
        this.account = account;
        this.type = type;
    }

    @Override
    public String toString() {
        return "FrontMember{" +
                "openAccount=" + openAccount +
                ", applyForOpenAccount=" + applyForOpenAccount +
                ", innerDiscAccount='" + innerDiscAccount + '\'' +
                ", account='" + account + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Boolean getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(Boolean openAccount) {
        this.openAccount = openAccount;
    }

    public Boolean getApplyForOpenAccount() {
        return applyForOpenAccount;
    }

    public void setApplyForOpenAccount(Boolean applyForOpenAccount) {
        this.applyForOpenAccount = applyForOpenAccount;
    }

    public String getInnerDiscAccount() {
        return innerDiscAccount;
    }

    public void setInnerDiscAccount(String innerDiscAccount) {
        this.innerDiscAccount = innerDiscAccount;
    }
}