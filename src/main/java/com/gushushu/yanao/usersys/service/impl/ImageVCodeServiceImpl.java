package com.gushushu.yanao.usersys.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.gushushu.yanao.usersys.cache.ObjectCache;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.model.ImageVCodeCache;
import com.gushushu.yanao.usersys.service.ImageVCodeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;


@Service
public class ImageVCodeServiceImpl implements ImageVCodeService {

    @Autowired
    private ObjectCache objectCache;

    private static DefaultKaptcha kaptcha = new DefaultKaptcha();

    static {
        InputStream is = ImageVCodeServiceImpl.class.getResourceAsStream("/kaptcha.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Config config = new Config(properties);
        kaptcha.setConfig(config);
    }

    static final Logger logger = Logger.getLogger(ImageVCodeServiceImpl.class);

    @Override
    public ResponseBody<BufferedImage> create(String session) {

        logger.info("------Method:\tImageVCodeServiceImpl.create-------");
        logger.info("session = [" + session + "]");
        ResponseBody rb = new ResponseBody();

        String code = kaptcha.createText();
        ImageVCodeCache imageVCodeCache = new ImageVCodeCache();
        imageVCodeCache.setSession(session);
        imageVCodeCache.setImageCode(code);
        //保存验证码到缓存
        objectCache.set(imageVCodeCache);


        BufferedImage bufferedImage =  kaptcha.createImage(String.valueOf(code));

        rb.success(bufferedImage);



        return rb;
    }

    @Override
    public ResponseBody validate(String session, String imageCode) {

        //instance.validateResponseForID(session,imageCode);

        logger.info("-------Method:\tImageVCodeServiceImpl.validate------");
        logger.info("session = [" + session + "], imageCode = [" + imageCode + "]");
        ResponseBody rb = new ResponseBody();

        ImageVCodeCache imageVCodeCache = new ImageVCodeCache();
        imageVCodeCache.setSession(session);

        imageVCodeCache = (ImageVCodeCache) objectCache.get(imageVCodeCache);
        if(imageVCodeCache == null){
            String errmsg = "invalid session";
            rb.error(errmsg);
            logger.warn(errmsg);
        }else if(!imageVCodeCache.getImageCode().equals(imageCode)){
            String errmsg = "图形验证码错误";
            rb.error(errmsg);
            logger.warn(errmsg);
        }else{
            objectCache.del(imageVCodeCache);
            rb.success();
        }



        return rb;
    }
}
