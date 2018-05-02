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


    public ResponseEntity<ResponseBody<T>> success(T data){
        ResponseBody<T> responseBody = new ResponseBody<T>();
        responseBody.status = 1;
        responseBody.data = data;
        return new ResponseEntity<ResponseBody<T>>(responseBody,HttpStatus.OK);
    }

    public ResponseEntity<ResponseBody<T>> failed(String msg){
        ResponseBody<T> responseBody = new ResponseBody<T>();
        responseBody.status = -1;
        responseBody.message = msg;
        return new ResponseEntity<ResponseBody<T>>(responseBody,HttpStatus.OK);
    }


    public static class ResponseBody<C>{
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

    }



}
