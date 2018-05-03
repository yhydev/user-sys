package com.gushushu.yanao.usersys.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public  class ResponseBody<C>{
    private Integer status;
    private C data;
    private String message;

    @Override
    public String toString() {
        return "ResponseBody{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }

    public boolean isSuccess(){
        return status == 1;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public C getData() {
        return data;
    }

    public void setData(C data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

