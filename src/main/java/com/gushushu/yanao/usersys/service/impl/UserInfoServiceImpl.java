package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.cache.ObjectCache;
import com.gushushu.yanao.usersys.common.RandomUtils;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.common.SecretEncode;
import com.gushushu.yanao.usersys.config.AppConstant;
import com.gushushu.yanao.usersys.entity.PhoneVCode;
import com.gushushu.yanao.usersys.entity.UserInfo;
import com.gushushu.yanao.usersys.model.BankInfo;
import com.gushushu.yanao.usersys.model.IdInfo;
import com.gushushu.yanao.usersys.model.UserToken;
import com.gushushu.yanao.usersys.repository.UserInfoRepository;
import com.gushushu.yanao.usersys.service.UserInfoService;
import com.gushushu.yanao.usersys.service.PhoneVCodeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.regex.Pattern;

@Service
public class UserInfoServiceImpl implements UserInfoService,AppConstant {

    final static Logger logger = Logger.getLogger(UserInfoServiceImpl.class);

    //注册验证码类型
    final static String REGISTER_VERIFICATION_CODE = "register";

    //找回密码验证码类型
    final static String FIND_PASSWORD_VERIFICATION_CODE = "findPassword";


    //用户token 超时时间(单位秒)
    final static int USER_TOKEN_TIMEOUT = 1800;


    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ObjectCache objectCache;

    @Autowired
    private PhoneVCodeService verificationCodeService;

    public ResponseEntity<UserInfo> register(String account, String code, String password) {

        logger.info("-----Method:\tRegister----");

        logger.info("param:\taccount:\t"+account);
        logger.info("param:\tcode:\t"+code);
        logger.info("param:\tpassword:\t"+password);

        ResponseEntity<PhoneVCode> smsRE = verificationCodeService.validateCode(account,REGISTER_VERIFICATION_CODE,code);
        //验证码是否正确
        if(smsRE.getStatusCode() == HttpStatus.OK){

            Long accountCount = userInfoRepository.countByAccount(account);

            if(accountCount != 0){
                String errmsg = "此手机号已被注册.";
                logger.warn(errmsg);
                return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
            }else{
                UserInfo userInfo = new UserInfo();
                userInfo.setCreateDate(new Date());
                userInfo.setPassword(SecretEncode.md5(password));
                userInfo.setAccount(account);
                userInfo.setPhone(account);
                userInfo.setBalance(0L);
                userInfoRepository.save(userInfo);
                
                return new ResponseEntity<UserInfo>(HttpStatus.OK);
            }
        }else{
        	return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, "验证码错误");
        }
    }

    public ResponseEntity<UserToken> login(String account, String password) {

        logger.info("-----Method:\tlogin----");
        logger.info("param:\taccount:\t"+account);
        logger.info("param:\tpassword:\t"+password);

        UserInfo userInfo = userInfoRepository.findByAccount(account);
        String encodePassword = SecretEncode.md5(password);

        if(userInfo != null && encodePassword.equals(userInfo.getPassword())){

            String token = RandomUtils.generate();

            UserToken userToken = new UserToken();
            userToken.setUserId(userInfo.getUserId());
            userToken.setToken(token);
            objectCache.set(userToken,USER_TOKEN_TIMEOUT);
            
            return new ResponseEntity<UserToken>(userToken, HttpStatus.OK);
        }else{
            String errmsg = "账号或密码错误";
            logger.warn(errmsg);
            
            return new ResponseEntityBuilder<UserToken>().builder(HttpStatus.BAD_REQUEST, ERROR,errmsg);
        }
    }

    @Transactional
    public ResponseEntity<UserInfo> findPassword(String account, String phoneCode, String password) {

        logger.info("-----Method:\tfindPassword----");
        logger.info("param:\taccount:\t"+account);
        logger.info("param:\tphoneCode:\t"+phoneCode);
        logger.info("param:\tpassword:\t"+password);

        if(StringUtils.isEmpty(password)){
            String errmsg = "invalid password";
            logger.info(errmsg);
            return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else{
        	ResponseEntity<PhoneVCode> verRE = verificationCodeService.validateCode(account,FIND_PASSWORD_VERIFICATION_CODE,phoneCode);
            if(verRE.getStatusCode() == HttpStatus.OK){//判断验证码
                String encodePassword = SecretEncode.md5(password);
               Integer updateCount = userInfoRepository.updatePassword(account,encodePassword);
               if(updateCount == 1){
                   return new ResponseEntity<UserInfo>(HttpStatus.OK);
               }else{
                   String errmsg = "invalid account";
                   logger.info(errmsg);
                   return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
               }
            }else{
            	 return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, "验证码错误");
            }
        }
    }

    @Transactional
    public ResponseEntity<UserInfo> updatePassword(String userId, String password,String newPassword) {

        logger.info("-----Method:\tupdatePassword----");
        logger.info("param:\tuserId:\t"+userId);
        logger.info("param:\tpassword:\t"+password);
        logger.info("param:\tnewPassword:\t"+newPassword);

        //判断旧密码是否错误
        if(StringUtils.isEmpty(password) || StringUtils.isEmpty(newPassword)){
            String errmsg = "password or newPassword invalid";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else if(password.equals(password)){
            String errmsg = "新密码不能与旧密码相同";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        } else{
            String encodePassword = SecretEncode.md5(password);
            String encodeNewPassword = SecretEncode.md5(newPassword);

            Integer updateCount = userInfoRepository.updatePassword(userId,encodePassword,encodeNewPassword);
            if(updateCount == 1){
                return new ResponseEntity<UserInfo>(HttpStatus.OK);
            }else{
                String errmsg = "旧密码无效";
                logger.info(errmsg);
                return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
            }
        }
    }

    @Transactional
    public ResponseEntity<UserInfo> updateEmail(String userId, String password,String email) {

        logger.info("-----Method:\tupdateEmail----");
        logger.info("param:\tuserId:\t"+userId);
        logger.info("param:\temail:\t"+email);
        logger.info("param:\tpassword:\t"+password);

        if(StringUtils.isEmpty(password)){
            String errmsg = "password invalid";
            logger.info(errmsg);
            return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else if(StringUtils.isEmpty(email) || !Pattern.matches(".+@.+\\..+",email)){
            String errmsg = "email invalid";
            logger.info(errmsg);
            return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else{
            String encodePassword = SecretEncode.md5(password);

            Integer updateCount = userInfoRepository.updateEmail(userId, encodePassword, email);
            if(updateCount == 1){
                return new ResponseEntity<UserInfo>(HttpStatus.OK);
            }else{
                String errmsg = "密码错误";
                logger.info(errmsg);
                return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
            }
        }
    }

    @Transactional
    public ResponseEntity<UserInfo> update(String userId, BankInfo bankInfo, IdInfo idInfo) {

        logger.info("-----Method:\tupdate----");
        logger.info("param:\tuserId:\t"+userId);
        logger.info("param:\tbankInfo:\t"+bankInfo);
        logger.info("param:\tidInfo:\t"+idInfo);

        UserInfo userInfo = userInfoRepository.findOne(userId);

        //TODO 认证身份证和姓名

        //TODO 认证银行卡和身份证

        //TODO 获取银行卡类型

        if(userInfo == null){
            String errmsg  = "invalid userId";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else if(!StringUtils.isEmpty(userInfo.getId())){
            String errmsg  = "您已实名认证,无需再次认证";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else{

            Integer updateCount = userInfoRepository.update(userId,idInfo.getId(),idInfo.getName(),
                    bankInfo.getBankCardNo(),bankInfo.getBankName());
            // TODO 发送短信通知管理员
            return new ResponseEntity<UserInfo>(HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<UserInfo> updateMoney(String userId,Long money){

        logger.info("-----Method:\tupdateMoney----");

        logger.info("param:\tuserId:\t"+userId);
        logger.info("param:\tmoney:\t"+money);

        if(money == null || money == 0){
            String errmsg = "invalid money";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else{

            if(money > 0){
                userInfoRepository.updateBalance(userId,money);
                return new ResponseEntity<UserInfo>(HttpStatus.OK);
            }else{
                UserInfo userInfo = userInfoRepository.findOne(userId);
                if(userInfo.getBalance() >= ~money+1){
                    userInfoRepository.updateBalance(userId,money);
                    return new ResponseEntity<UserInfo>(HttpStatus.OK);
                }else{
                    String errmsg = "账户余额不足";
                    logger.warn(errmsg);
                    return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
                }
            }

        }
    }

    @Override
    public ResponseEntity<UserInfo> updateOuterDiscAccount(String userId, String outerDiscAccount) {

        logger.info("------Method:\tUserInfoServiceImpl.updateOuterDiscAccount");
        logger.info("userId = [" + userId + "], outerDiscAccount = [" + outerDiscAccount + "]");

        int updateCount = userInfoRepository.updateOuterDiscAccount(userId,outerDiscAccount);

        if(updateCount == 1){
            return new ResponseEntity<UserInfo>(HttpStatus.OK);
        }else if(updateCount == 0){
            String errmsg = "invalid userId or outerDiscAccount";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else{
            logger.error("------Method:\tUserInfoServiceImpl.updateOuterDiscAccount");
            logger.error("userId = [" + userId + "], outerDiscAccount = [" + outerDiscAccount + "]");
            logger.error("updateCount = " + updateCount);
            
            return new ResponseEntityBuilder<UserInfo>().builder(HttpStatus.BAD_REQUEST, ERROR, "错误");
        }
    }


}
