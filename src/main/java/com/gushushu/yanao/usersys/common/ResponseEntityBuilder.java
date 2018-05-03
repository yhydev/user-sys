package com.gushushu.yanao.usersys.common;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseEntityBuilder<T> {

    public ResponseEntityBuilder(){}

    public ResponseEntity<T> builder(HttpStatus httpStatus,String key,String value){
        Map errmsg = new HashMap();
        errmsg.put(key,value);
        return new ResponseEntity(errmsg,httpStatus);
    }


    public static <T> ResponseEntity<ResponseBody<T>> success(T data){
        ResponseBody<T> responseBody = new ResponseBody<T>();
        responseBody.setStatus( 1);
        responseBody.setData(data);
        return new ResponseEntity<ResponseBody<T>>(responseBody,HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseBody<T>> failed(String msg){
        ResponseBody<T> responseBody = new ResponseBody<T>();
        responseBody.setStatus(-1);
        responseBody.setMessage(msg);
        return new ResponseEntity<ResponseBody<T>>(responseBody,HttpStatus.OK);
    }





}
