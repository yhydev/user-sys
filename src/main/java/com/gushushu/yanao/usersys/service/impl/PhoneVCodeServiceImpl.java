package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.cache.ObjectCache;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.config.AppConstant;
import com.gushushu.yanao.usersys.entity.PhoneVCode;
import com.gushushu.yanao.usersys.model.PhoneVCodeCache;
import com.gushushu.yanao.usersys.repository.PhoneVCodeRepository;
import com.gushushu.yanao.usersys.service.PhoneVCodeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

@Service
public class PhoneVCodeServiceImpl implements PhoneVCodeService,AppConstant {

    final static Logger logger = Logger.getLogger(PhoneVCodeServiceImpl.class);


    private static Properties limit;

    static {
        InputStream is = PhoneVCodeServiceImpl.class.getResourceAsStream("/vcodeLimit.properties");
        limit = new Properties();
        try {
            limit.load(is);
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private PhoneVCodeRepository verificationCodeRepository;

    @Autowired
    private ObjectCache objectCache;

    //final static long timeout = 60 * 1000 * 5;


    public ResponseEntity<PhoneVCode> send(String phone,String type) {


        logger.info("----Method:\tsend----");
        logger.info("param:\t phone\t"+phone);
        logger.info("param:\t type\t"+type);

        ResponseBody rb = new ResponseBody();
        Integer limitSec = getLimit(type);

        if(limit == null){
            String errmsg = "invalid type";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<PhoneVCode>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else{
            Random random = new Random();
            String code = String.valueOf(random.nextInt(999999));
            rb = validatePhone(phone,type);

            if(rb.getSuccess()){
                //保存到数据库
                PhoneVCode verificationCode = new PhoneVCode();
                verificationCode.setCode(code);
                verificationCode.setCreateDate(new Date());
                verificationCode.setPhone(phone);
                verificationCode.setType(type);
                verificationCodeRepository.save(verificationCode);

                //保存到缓存
                PhoneVCodeCache verificationCodeCache = new PhoneVCodeCache();
                verificationCodeCache.setVerificationCodeId(verificationCode.getVerificationCodeId());
                verificationCodeCache.setCode(verificationCode.getCode());
                verificationCodeCache.setType(verificationCode.getType());
                verificationCodeCache.setStatus(verificationCode.getStatus());
                verificationCodeCache.setPhone(verificationCode.getPhone());
                verificationCodeCache.setCreateDate(verificationCode.getCreateDate());


                objectCache.set(verificationCodeCache,limitSec);

                //TODO SMS 发送验证码

                return new ResponseEntity<PhoneVCode>(verificationCode, HttpStatus.OK);
            }else{
            	return new ResponseEntityBuilder<PhoneVCode>().builder(HttpStatus.BAD_REQUEST, ERROR, "信息发送失败");
            }
        }
    }

    public ResponseEntity<PhoneVCode> validateCode(String phone,String type, String code) {

        logger.info("----Method:\tvalidateCode----");
        logger.info("param:\t phone\t"+phone);
        logger.info("param:\t type\t" + type);
        logger.info("param:\t code\t"+code);

        PhoneVCodeCache verificationCodeCache = new PhoneVCodeCache();
        verificationCodeCache.setPhone(phone);
        verificationCodeCache.setType(type);

        verificationCodeCache = (PhoneVCodeCache) objectCache.get(verificationCodeCache);

        if(verificationCodeCache == null || !code.equals(verificationCodeCache.getCode())){
            String errmsg = "验证码无效";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<PhoneVCode>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg); 
        }else{
            return new ResponseEntity<PhoneVCode>(HttpStatus.OK);
        }
    }




    private ResponseBody validatePhone(String phone,String type) {

        logger.info("----Method:\tvalidatePhone----");
        logger.info("param phone:\t"+phone);
        logger.info("param type:\t"+type);
        ResponseBody rb = new ResponseBody();

        Integer limitSec = getLimit(type);
        if(limitSec == null){
            String errmsg = "invalid type";
            rb.error(errmsg);
            logger.warn(errmsg);
        }else{
            PhoneVCodeCache phoneInfo = new PhoneVCodeCache();
            phoneInfo.setPhone(phone);
            phoneInfo.setType(type);
            PhoneVCodeCache phoneInfoCache = (PhoneVCodeCache) objectCache.get(phoneInfo);


            if(phoneInfoCache == null){
                rb.success();
                logger.info("success");
            }else{
                Long now = System.currentTimeMillis();
                Long timeout = limitSec  - (now  - phoneInfoCache.getCreateDate().getTime()) /1000;

                String errmsg = "请"+timeout+"秒后再发送.";
                rb.error(errmsg);
                logger.warn(errmsg);
            }

        }



        return rb;
    }



    private Integer getLimit(String type){
        String limitstr = null;
        Integer limitSec = null;

        if(type != null && (limitstr = limit.getProperty(type)) != null){
            limitSec = Integer.valueOf(limitstr);
        }

        return limitSec;
    }

}
