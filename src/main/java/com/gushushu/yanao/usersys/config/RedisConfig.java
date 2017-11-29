package com.gushushu.yanao.usersys.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "redisConfig")
public class RedisConfig {

    private String address;
    private Integer port = 6379;
    private String password;
    private Integer timeout = 10000;
    private Integer maxIdle = 8;
    private Integer minIdle = 0;
    private Integer maxActive = 20;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }


    @Override
    public String toString() {
        return "RedisConfig{" +
                "address='" + address + '\'' +
                ", port=" + port +
                ", password='" + password + '\'' +
                ", timeout=" + timeout +
                ", maxIdle=" + maxIdle +
                ", minIdle=" + minIdle +
                ", maxActive=" + maxActive +
                '}';
    }


}
