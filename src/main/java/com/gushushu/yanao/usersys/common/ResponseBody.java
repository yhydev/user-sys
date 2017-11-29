package com.gushushu.yanao.usersys.common;

public class ResponseBody<T> {



    private Integer status;
    private T data;
    private String message;
    private Boolean success;


    public ResponseBody success(T data){
        this.data = data;
        return success();
    }

    public ResponseBody error(String message){
        this.message = message;
        return error();
    }


    public ResponseBody success(){
        this.status = 1;
        this.success = true;
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ResponseBody error(){
        this.status = 0;
        this.success = false;
        return this;
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
