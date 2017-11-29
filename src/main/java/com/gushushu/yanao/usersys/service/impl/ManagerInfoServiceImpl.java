package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.cache.ObjectCache;
import com.gushushu.yanao.usersys.common.RandomUtils;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.SecretEncode;
import com.gushushu.yanao.usersys.entity.ManagerInfo;
import com.gushushu.yanao.usersys.model.ManagerToken;
import com.gushushu.yanao.usersys.repository.ManagerInfoRepository;
import com.gushushu.yanao.usersys.service.ManagerInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
public class ManagerInfoServiceImpl implements ManagerInfoService {

    @Autowired
    private ManagerInfoRepository managerInfoRepository;

    @Autowired
    private ObjectCache objectCache;
    
    final static Logger logger = Logger.getLogger(ManagerInfoServiceImpl.class);

    @Override
    @Transactional
    public ResponseBody<ManagerInfo> createNotExists(String account, String password) {

        logger.info("------Method:\tManagerInfoServiceImpl.createNotExists");
        logger.info("account = [" + account + "], password = [" + password + "]");
        
        ResponseBody rb = new ResponseBody();
        
        if(StringUtils.isEmpty(account)){
            String errmsg = "invalid account";
            rb.error(errmsg);
            logger.warn(errmsg);
        }else if(StringUtils.isEmpty(password)){
            String errmsg = "invalid password";
            rb.error(errmsg);
            logger.warn(errmsg);
        }else{
            ManagerInfo managerInfo = managerInfoRepository.findByAccount(account);
            
            if(managerInfo == null){
                managerInfo = new ManagerInfo();
                managerInfo.setAccount(account);
                managerInfo.setPassword(SecretEncode.md5(password));
                managerInfoRepository.save(managerInfo);
            }
            rb.success(managerInfo);
        }
        
        return rb;
    }

    @Override
    @Transactional
    public ResponseBody updatePassword(String managerId, String password,String newPassword) {

        logger.info("------Method:\tManagerInfoServiceImpl.updatePassword");
        logger.info("managerId = [" + managerId + "], password = [" + password + "], newPassword = [" + newPassword + "]");
        ResponseBody rb = new ResponseBody();
        
        if(StringUtils.isEmpty(password)){
            String errmsg = "invalid password";
            rb.error(errmsg);
            logger.warn(errmsg);
        }else if(password.equals(newPassword)){
            String errmsg = "新的密码不能和旧密码相同";
            rb.error(errmsg);
            logger.warn(errmsg);
        }else{
            String encodePassword = SecretEncode.md5(password);
            String encodeNewPassword = SecretEncode.md5(newPassword);
            
            int updateCount = managerInfoRepository.updatePassword(managerId,encodePassword,encodeNewPassword);
            
            if(updateCount == 1){
                rb.success();
            }else if(updateCount == 0){
                String errmsg = "旧密码错误";
                rb.error(errmsg);
                logger.warn(errmsg);
            }else {
                logger.error("updateCount = " + updateCount);
            }
            
        }
        
        
        return rb;
    }

    @Override
    public ResponseBody<ManagerToken> login(String account, String password) {
        logger.info("------Method:\tManagerInfoServiceImpl.login");
        logger.info("account = [" + account + "], password = [" + password + "]");

        ResponseBody rb = new ResponseBody();
        if(StringUtils.isEmpty(account)){
            String errmsg = "账号不能为空";
            rb.error(errmsg);
            logger.warn(errmsg);
        }else if(StringUtils.isEmpty(password)){
            String errmsg = "密码不能为空";
            rb.error(errmsg);
            logger.warn(errmsg);
        }else{
            String encodePassword = SecretEncode.md5(password);
            ManagerInfo managerInfo = managerInfoRepository.findByAccount(account);

            if(managerInfo == null){//没有查找到用户
                String errmsg = "用户不能为空";
                rb.error("密码错误");
                logger.warn("invalid managerId");
            }else if(encodePassword.equals(managerInfo.getPassword())){//密码正确

                String token = RandomUtils.generate();
                ManagerToken managerToken =  new ManagerToken(token);
                managerToken.setManagerId(managerInfo.getManagerId());

                objectCache.set(managerToken);

                rb.success(managerToken);

            }else{//密码错误
                rb.error("密码错误");
                logger.warn("密码错误");
            }

        }


        return rb;
    }
}
