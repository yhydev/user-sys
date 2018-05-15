package com.gushushu.yanao.usersys.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.service.OSSService;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;

@Service
public class OSSServiceImpl implements OSSService {

    Logger logger = Logger.getLogger(this.getClass());

    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.directory}")
    private String directory;
    @Value("${oss.bucketName}")
    private String bucketName;

    private OSSClient ossClient;


    @Override
    public ResponseEntity<ResponseBody<String>> upload(InputStream inputStream, String filename) {
        if(ossClient == null){
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        }


        System.out.println("inputStream = [" + inputStream + "], filename = [" + filename + "]");
        ResponseEntity response = null;
        try {

            String key = String.format("%s/%s-%s",directory,new Date().getTime(),filename);
            PutObjectResult putObjectResult = ossClient.putObject(bucketName,key, inputStream);
            response = ResponseEntityBuilder.success(String.format("http://%s.%s/%s",bucketName,endpoint,key));

        }catch (Exception e){
            response = ResponseEntityBuilder.failed(e.getMessage());
        }

        System.out.println("response = " + response);

        return response;
    }







}
