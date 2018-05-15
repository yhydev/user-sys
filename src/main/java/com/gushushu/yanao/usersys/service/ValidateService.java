package com.gushushu.yanao.usersys.service;

import com.gushushu.yanao.usersys.common.ResponseBody;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

public interface ValidateService {


    ResponseEntity<ResponseBody<String>> bankCard(String bankCard);


}
