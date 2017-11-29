package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.cache.ObjectCache;
import com.gushushu.yanao.usersys.common.RandomUtils;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.SecretEncode;
import com.gushushu.yanao.usersys.entity.UserInfo;
import com.gushushu.yanao.usersys.model.BankInfo;
import com.gushushu.yanao.usersys.model.IdInfo;
import com.gushushu.yanao.usersys.model.UserToken;
import com.gushushu.yanao.usersys.repository.UserInfoRepository;
import com.gushushu.yanao.usersys.service.UserInfoService;
import com.gushushu.yanao.usersys.service.PhoneVCodeService;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.regex.Pattern;

@Service
public class UserInfoServiceImpl implements UserInfoService {

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

    public ResponseBody register(String account, String code, String password) {

        logger.info("-----Method:\tRegister----");

        logger.info("param:\taccount:\t"+account);
        logger.info("param:\tcode:\t"+code);
        logger.info("param:\tpassword:\t"+password);

        ResponseBody rb = new ResponseBody();

        ResponseBody smsRB = verificationCodeService.validateCode(account,REGISTER_VERIFICATION_CODE,code);
        //验证码是否正确
        if(smsRB.getSuccess()){

            Long accountCount = userInfoRepository.countByAccount(account);

            if(accountCount != 0){
                String errmsg = "此手机号已被注册.";
                logger.warn(errmsg);
                rb.error(errmsg);
            }else{
                UserInfo userInfo = new UserInfo();
                userInfo.setCreateDate(new Date());
                userInfo.setPassword(SecretEncode.md5(password));
                userInfo.setAccount(account);
                userInfo.setPhone(account);
                userInfo.setBalance(0L);
                userInfoRepository.save(userInfo);
                rb.success();
            }


        }else{
            rb = smsRB;
        }


        return rb;
    }

    public ResponseBody login(String account, String password) {

        logger.info("-----Method:\tlogin----");
        logger.info("param:\taccount:\t"+account);
        logger.info("param:\tpassword:\t"+password);

        ResponseBody rb = new ResponseBody();

        UserInfo userInfo = userInfoRepository.findByAccount(account);
        String encodePassword = SecretEncode.md5(password);

        if(userInfo != null && encodePassword.equals(userInfo.getPassword())){

            String token = RandomUtils.generate();

            UserToken userToken = new UserToken();
            userToken.setUserId(userInfo.getUserId());
            userToken.setToken(token);
            objectCache.set(userToken,USER_TOKEN_TIMEOUT);
            rb.success(userToken);
        }else{

            String errmsg = "账号或密码错误";
            rb.error(errmsg);
            logger.warn(errmsg);
        }


        return rb;
    }

    @Transactional
    public ResponseBody findPassword(String account, String phoneCode, String password) {

        logger.info("-----Method:\tfindPassword----");
        logger.info("param:\taccount:\t"+account);
        logger.info("param:\tphoneCode:\t"+phoneCode);
        logger.info("param:\tpassword:\t"+password);

        ResponseBody rb = new ResponseBody();

        if(StringUtils.isEmpty(password)){
            String errmsg = "invalid password";
            logger.info(errmsg);
            rb.error(errmsg);
        }else{

            ResponseBody verRB = verificationCodeService.validateCode(account,FIND_PASSWORD_VERIFICATION_CODE,phoneCode);
            if(verRB.getSuccess()){//判断验证码
                String encodePassword = SecretEncode.md5(password);
               Integer updateCount = userInfoRepository.updatePassword(account,encodePassword);
               if(updateCount == 1){
                   rb.success();
               }else{
                   String errmsg = "invalid account";
                   logger.info(errmsg);
                   rb.error(errmsg);
               }

            }else{
                rb = verRB;
            }

        }

        return rb;
    }

    @Transactional
    public ResponseBody updatePassword(String userId, String password,String newPassword) {

        logger.info("-----Method:\tupdatePassword----");
        logger.info("param:\tuserId:\t"+userId);
        logger.info("param:\tpassword:\t"+password);
        logger.info("param:\tnewPassword:\t"+newPassword);

        ResponseBody rb = new ResponseBody();

        //判断旧密码是否错误
        if(StringUtils.isEmpty(password) || StringUtils.isEmpty(newPassword)){
            String errmsg = "password or newPassword invalid";
            rb.error(errmsg);
            logger.warn(errmsg);
        }else if(password.equals(password)){
            String errmsg = "新密码不能与旧密码相同";
            rb.error(errmsg);
            logger.warn(errmsg);
        } else{
            String encodePassword = SecretEncode.md5(password);
            String encodeNewPassword = SecretEncode.md5(newPassword);

            Integer updateCount = userInfoRepository.updatePassword(userId,password,newPassword);
            if(updateCount == 1){
                rb.success();
            }else{
                String errmsg = "旧密码无效";
                rb.error(errmsg);
                logger.info(errmsg);
            }

        }


        return rb;

    }

    @Transactional
    public ResponseBody updateEmail(String userId, String password,String email) {

        logger.info("-----Method:\tupdateEmail----");
        logger.info("param:\tuserId:\t"+userId);
        logger.info("param:\temail:\t"+email);
        logger.info("param:\tpassword:\t"+password);

        ResponseBody rb = new ResponseBody();

        if(StringUtils.isEmpty(password)){
            String errmsg = "password invalid";
            rb.error(errmsg);
            logger.info(errmsg);
        }else if(StringUtils.isEmpty(email) || !Pattern.matches(".+@.+\\..+",email)){
            String errmsg = "email invalid";
            rb.error(errmsg);
            logger.info(errmsg);
        }else{

            String encodePassword = SecretEncode.md5(password);

            Integer updateCount = userInfoRepository.updateEmail(userId, encodePassword, email);
            if(updateCount == 1){
                rb.success();
            }else{
                String errmsg = "密码错误";
                rb.error(errmsg);
                logger.info(errmsg);
            }
        }

        return rb;
    }

    @Transactional
    public ResponseBody update(String userId, BankInfo bankInfo, IdInfo idInfo) {

        logger.info("-----Method:\tupdate----");
        logger.info("param:\tuserId:\t"+userId);
        logger.info("param:\tbankInfo:\t"+bankInfo);
        logger.info("param:\tidInfo:\t"+idInfo);


        ResponseBody rb = new ResponseBody();

        UserInfo userInfo = userInfoRepository.findOne(userId);

        //TODO 认证身份证和姓名

        //TODO 认证银行卡和身份证

        //TODO 获取银行卡类型

        if(userInfo == null){
            String errmsg  = "invalid userId";
            logger.warn(errmsg);
            rb.error(errmsg);
        }else if(!StringUtils.isEmpty(userInfo.getId())){
            String errmsg  = "您已实名认证,无需再次认证";
            logger.warn(errmsg);
            rb.error(errmsg);
        }else{

            Integer updateCount = userInfoRepository.update(userId,idInfo.getId(),idInfo.getName(),
                    bankInfo.getBankCardNo(),bankInfo.getBankName());
            // TODO 发送短信通知管理员
            rb.success();
        }

        return rb;
    }

    @Transactional
    public ResponseBody updateMoney(String userId,Long money){

        logger.info("-----Method:\tupdateMoney----");

        logger.info("param:\tuserId:\t"+userId);
        logger.info("param:\tmoney:\t"+money);

        ResponseBody rb = new ResponseBody();

        if(money == null || money == 0){
            String errmsg = "invalid money";
            logger.warn(errmsg);
            rb.error(errmsg);
        }else{

            if(money > 0){
                userInfoRepository.updateBalance(userId,money);
                rb.success();
            }else{
                UserInfo userInfo = userInfoRepository.findOne(userId);
                if(userInfo.getBalance() >= ~money+1){
                    userInfoRepository.updateBalance(userId,money);
                    rb.success();
                }else{
                    String errmsg = "账户余额不足";
                    logger.warn(errmsg);
                    rb.error(errmsg);
                }
            }

        }


        return rb;

    }

    @Override
    public ResponseBody updateOuterDiscAccount(String userId, String outerDiscAccount) {

        logger.info("------Method:\tUserInfoServiceImpl.updateOuterDiscAccount");
        logger.info("userId = [" + userId + "], outerDiscAccount = [" + outerDiscAccount + "]");

        ResponseBody rb = new ResponseBody();

        int updateCount = userInfoRepository.updateOuterDiscAccount(userId,outerDiscAccount);

        if(updateCount == 1){
            rb.success();
        }else if(updateCount == 0){
            String errmsg = "invalid userId or outerDiscAccount";
            logger.warn(errmsg);
            rb.error(errmsg);
        }else{
            logger.error("------Method:\tUserInfoServiceImpl.updateOuterDiscAccount");
            logger.error("userId = [" + userId + "], outerDiscAccount = [" + outerDiscAccount + "]");
            logger.error("updateCount = " + updateCount);
        }

        return rb;
    }


}
