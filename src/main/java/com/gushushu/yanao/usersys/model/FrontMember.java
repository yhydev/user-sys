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

    public FrontMember(Boolean openAccount, Boolean applyForOpenAccount, String innerDiscAccount) {
        this.openAccount = openAccount;
        this.applyForOpenAccount = applyForOpenAccount;
        this.innerDiscAccount = innerDiscAccount;
    }

    @Override
    public String toString() {
        return "FrontMember{" +
                "openAccount=" + openAccount +
                ", applyForOpenAccount=" + applyForOpenAccount +
                ", innerDiscAccount='" + innerDiscAccount + '\'' +
                '}';
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