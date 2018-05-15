package com.gushushu.yanao.usersys.service;

import com.gushushu.yanao.usersys.common.ResponseBody;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;

public interface OSSService {

    ResponseEntity<ResponseBody<String>> upload(InputStream inputStream,String filename);



}
