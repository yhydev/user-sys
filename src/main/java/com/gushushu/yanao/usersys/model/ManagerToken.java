package com.gushushu.yanao.usersys.model;

import com.gushushu.yanao.usersys.cache.CacheObject;

public class ManagerToken implements CacheObject {

    private String token;
    private String managerId;

    public ManagerToken() {}

    public ManagerToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return "managerToken_" + token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getManagerId() {
        return managerId;
    }

    @Override
    public String toString() {
        return "ManagerToken{" +
                "token='" + token + '\'' +
                ", managerId='" + managerId + '\'' +
                '}';
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
}
