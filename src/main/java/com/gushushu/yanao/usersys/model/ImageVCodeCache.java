package com.gushushu.yanao.usersys.model;

import com.gushushu.yanao.usersys.cache.CacheObject;

public class ImageVCodeCache implements CacheObject {

    private String session;
    private String imageCode;

    @Override
    public String toString() {
        return "ImageVCodeCache{" +
                "session='" + session + '\'' +
                ", imageCode='" + imageCode + '\'' +
                '}';
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    @Override
    public String getKey() {
        return "imageVCodeCache_"+session;
    }
}
