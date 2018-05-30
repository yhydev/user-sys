package com.gushushu.yanao.usersys.model;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * front model ,it show to member self
 *
 */
public class FrontMember {

    private String innerDiscAccount;
    private String account;
    private String type;
    private String openAccountStatus;


    public FrontMember(String openAccount, String innerDiscAccount,String account,String type) {
        this.openAccountStatus = openAccount;
        this.innerDiscAccount = innerDiscAccount;
        this.account = account;
        this.type = type;
    }

    public String getInnerDiscAccount() {
        return innerDiscAccount;
    }

    public void setInnerDiscAccount(String innerDiscAccount) {
        this.innerDiscAccount = innerDiscAccount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOpenAccountStatus() {
        return openAccountStatus;
    }

    public void setOpenAccountStatus(String openAccountStatus) {
        this.openAccountStatus = openAccountStatus;
    }
}