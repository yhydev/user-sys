package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.common.RandomUtils;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.config.AppConstant;
import com.gushushu.yanao.usersys.entity.IdentifyingCode;
import com.gushushu.yanao.usersys.repository.IdentifyingCodeRepository;
import com.gushushu.yanao.usersys.service.IdentifyingCodeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

@Service
public class IdentifyingCodeServiceImpl implements IdentifyingCodeService,AppConstant {

    final static Logger logger = Logger.getLogger(IdentifyingCodeServiceImpl.class);

    @Autowired
    private IdentifyingCodeRepository identifyingCodeRepository;



    public static final int INTERVAL_TIME = 60 * 1000;

    public static final String UNVERIFIED = "Unverified";

    public static final String VERIFIED = "verified";

    private boolean isInterval(String phone){
        Date createDate = identifyingCodeRepository.findLastTime(phone);
        if(createDate == null ||
                ( createDate.getTime() + INTERVAL_TIME ) < new Date().getTime()){
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public ResponseEntity send(SendParam sendParam) {

        logger.info("sendParam = [" + sendParam + "]");

        ResponseEntity responseEntity = null;

        if(isInterval(sendParam.phone)){

            IdentifyingCode identifyingCode = new IdentifyingCode();
            identifyingCode.setCode(random.nextInt(899999) + 100000);
            identifyingCode.setPhone(sendParam.phone);
            identifyingCode.setType(sendParam.type);
            identifyingCode.setCreateDate(new Date());
            identifyingCode.setStatus(UNVERIFIED);

            identifyingCodeRepository.save(identifyingCode);
            //TODO 发送验证码接口调用
            responseEntity = new ResponseEntityBuilder().success(null);

        }else {
            responseEntity = new ResponseEntityBuilder().failed("一分钟只能发送一条验证码");
        }

        logger.info("responseEntity = " + responseEntity);

        return responseEntity;
    }

    @Override
    @Transactional
    public ResponseEntity<IdentifyingCode> validate(ValidateParam param) {

        logger.info("param = [" + param + "]");
        ResponseEntity entity = null;

        IdentifyingCode code = identifyingCodeRepository.findCode(param.phone,param.type,UNVERIFIED,param.code);

        if(code != null){
            code.setStatus(VERIFIED);
            identifyingCodeRepository.save(code);
            entity = new ResponseEntityBuilder().success(null);
        }else{
            entity = new ResponseEntityBuilder().failed("手机验证码有误");
        }

        logger.info("entity = " + entity);
        return entity;
    }
}