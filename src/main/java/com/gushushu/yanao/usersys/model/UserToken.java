package com.gushushu.yanao.usersys.model;

import com.gushushu.yanao.usersys.cache.CacheObject;

public class UserToken implements CacheObject {

    private String token;
    private String userId;

    public UserToken() {}

    public UserToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return "userToken_" + token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
