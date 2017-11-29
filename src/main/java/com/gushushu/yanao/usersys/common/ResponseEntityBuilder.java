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






}
