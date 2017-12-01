package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.cache.ObjectCache;
import com.gushushu.yanao.usersys.common.RandomUtils;
import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.common.SecretEncode;
import com.gushushu.yanao.usersys.config.AppConstant;
import com.gushushu.yanao.usersys.entity.ManagerInfo;
import com.gushushu.yanao.usersys.model.ManagerToken;
import com.gushushu.yanao.usersys.repository.ManagerInfoRepository;
import com.gushushu.yanao.usersys.service.ManagerInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
public class ManagerInfoServiceImpl implements ManagerInfoService,AppConstant {

    @Autowired
    private ManagerInfoRepository managerInfoRepository;

    @Autowired
    private ObjectCache objectCache;
    
    final static Logger logger = Logger.getLogger(ManagerInfoServiceImpl.class);

    @Override
    @Transactional
    public ResponseEntity<ManagerInfo> createNotExists(String account, String password) {

        logger.info("------Method:\tManagerInfoServiceImpl.createNotExists");
        logger.info("account = [" + account + "], password = [" + password + "]");
        
        if(StringUtils.isEmpty(account)){
            String errmsg = "invalid account";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<ManagerInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else if(StringUtils.isEmpty(password)){
            String errmsg = "invalid password";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<ManagerInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else{
            ManagerInfo managerInfo = managerInfoRepository.findByAccount(account);
            
            if(managerInfo == null){
                managerInfo = new ManagerInfo();
                managerInfo.setAccount(account);
                managerInfo.setPassword(SecretEncode.md5(password));
                managerInfoRepository.save(managerInfo);
            }
            return new ResponseEntity<ManagerInfo>(managerInfo, HttpStatus.OK);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ManagerInfo> updatePassword(String managerId, String password,String newPassword) {

        logger.info("------Method:\tManagerInfoServiceImpl.updatePassword");
        logger.info("managerId = [" + managerId + "], password = [" + password + "], newPassword = [" + newPassword + "]");
        
        if(StringUtils.isEmpty(password)){
            String errmsg = "invalid password";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<ManagerInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else if(password.equals(newPassword)){
            String errmsg = "新的密码不能和旧密码相同";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<ManagerInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else{
            String encodePassword = SecretEncode.md5(password);
            String encodeNewPassword = SecretEncode.md5(newPassword);
            
            int updateCount = managerInfoRepository.updatePassword(managerId,encodePassword,encodeNewPassword);
            
            if(updateCount == 1){
                return new ResponseEntity<ManagerInfo>(HttpStatus.OK);
            }else if(updateCount == 0){
                String errmsg = "旧密码错误";
                logger.warn(errmsg);
                return new ResponseEntityBuilder<ManagerInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
            }else {
                logger.error("updateCount = " + updateCount);
                return new ResponseEntityBuilder<ManagerInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, "错误");
            }
        }
    }

    @Override
    public ResponseEntity<ManagerToken> login(String account, String password) {
        logger.info("------Method:\tManagerInfoServiceImpl.login");
        logger.info("account = [" + account + "], password = [" + password + "]");

        if(StringUtils.isEmpty(account)){
            String errmsg = "账号不能为空";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<ManagerToken>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else if(StringUtils.isEmpty(password)){
            String errmsg = "密码不能为空";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<ManagerToken>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else{
            String encodePassword = SecretEncode.md5(password);
            ManagerInfo managerInfo = managerInfoRepository.findByAccount(account);

            if(managerInfo == null){//没有查找到用户
                String errmsg = "用户不能为空";
                logger.warn("invalid managerId");
                return new ResponseEntityBuilder<ManagerToken>().builder(HttpStatus.BAD_REQUEST, ERROR, "密码错误");
            }else if(encodePassword.equals(managerInfo.getPassword())){//密码正确

                String token = RandomUtils.generate();
                ManagerToken managerToken =  new ManagerToken(token);
                managerToken.setManagerId(managerInfo.getManagerId());

                objectCache.set(managerToken);

                return new ResponseEntity<ManagerToken>(managerToken, HttpStatus.OK);

            }else{//密码错误
                logger.warn("密码错误");
                return new ResponseEntityBuilder<ManagerToken>().builder(HttpStatus.BAD_REQUEST, ERROR, "密码错误");
            }

        }
    }
}
