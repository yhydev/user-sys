package com.gushushu.yanao.usersys.model;

public class FrontMemberSession {

    private String memberId;
    private String token;
    private String account;

    public FrontMemberSession(String memberId, String token, String account) {
        this.memberId = memberId;
        this.token = token;
        this.account = account;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "FrontMemberSession{" +
                "memberId='" + memberId + '\'' +
                ", token='" + token + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
