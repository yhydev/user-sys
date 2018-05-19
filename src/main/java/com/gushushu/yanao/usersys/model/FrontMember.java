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



    public FrontMember(Boolean openAccount, Boolean applyForOpenAccount, String innerDiscAccount,String account) {
        this.openAccount = openAccount;
        this.applyForOpenAccount = applyForOpenAccount;
        this.innerDiscAccount = innerDiscAccount;
        this.account = account;
    }

    @Override
    public String toString() {
        return "FrontMember{" +
                "openAccount=" + openAccount +
                ", applyForOpenAccount=" + applyForOpenAccount +
                ", innerDiscAccount='" + innerDiscAccount + '\'' +
                ", account='" + account + '\'' +
                '}';
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