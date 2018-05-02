package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.common.RandomUtils;
import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.common.SecretEncode;
import com.gushushu.yanao.usersys.entity.IdentifyingCode;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.model.BankInfo;
import com.gushushu.yanao.usersys.model.IdInfo;
import com.gushushu.yanao.usersys.repository.MemberRepository;
import com.gushushu.yanao.usersys.service.MemberService;
import com.gushushu.yanao.usersys.service.IdentifyingCodeService;
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
public class MemberServiceImpl implements MemberService {

    final static Logger logger = Logger.getLogger(MemberServiceImpl.class);

    //注册验证码类型
    final static String REGISTER_VERIFICATION_CODE = "register";

    //找回密码验证码类型
    final static String FIND_PASSWORD_VERIFICATION_CODE = "findPassword";


    //用户token 超时时间(单位秒)
    final static int USER_TOKEN_TIMEOUT = 1800;


    @Autowired
    private MemberRepository MemberRepository;

    @Autowired
    private IdentifyingCodeService identifyingCodeService;

    public ResponseEntity<Member> register(String account, String code, String password) {

        logger.info("-----Method:\tRegister----");

        logger.info("param:\taccount:\t"+account);
        logger.info("param:\tcode:\t"+code);
        logger.info("param:\tpassword:\t"+password);

        ResponseEntity<IdentifyingCode> smsRE = identifyingCodeService.validate(account,REGISTER_VERIFICATION_CODE,code);
        //验证码是否正确
        if(smsRE.getStatusCode() == HttpStatus.OK){

            Long accountCount = MemberRepository.countByAccount(account);

            if(accountCount != 0){
                String errmsg = "此手机号已被注册.";
                logger.warn(errmsg);
                return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
            }else{
                Member Member = new Member();
                Member.setCreateDate(new Date());
                Member.setPassword(SecretEncode.md5(password));
                Member.setAccount(account);
                Member.setBalance(0L);
                MemberRepository.save(Member);
                
                return new ResponseEntity<Member>(HttpStatus.OK);
            }
        }else{
        	return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, "验证码错误");
        }
    }

    public ResponseEntity<UserToken> login(String account, String password) {

        logger.info("-----Method:\tlogin----");
        logger.info("param:\taccount:\t"+account);
        logger.info("param:\tpassword:\t"+password);

        Member Member = MemberRepository.findByAccount(account);
        String encodePassword = SecretEncode.md5(password);

        if(Member != null && encodePassword.equals(Member.getPassword())){

            String token = RandomUtils.generate();

            UserToken userToken = new UserToken();
            userToken.setUserId(Member.getUserId());
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
    public ResponseEntity<Member> findPassword(String account, String phoneCode, String password) {

        logger.info("-----Method:\tfindPassword----");
        logger.info("param:\taccount:\t"+account);
        logger.info("param:\tphoneCode:\t"+phoneCode);
        logger.info("param:\tpassword:\t"+password);

        if(StringUtils.isEmpty(password)){
            String errmsg = "invalid password";
            logger.info(errmsg);
            return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else{
        	ResponseEntity<PhoneVCode> verRE = verificationCodeService.validateCode(account,FIND_PASSWORD_VERIFICATION_CODE,phoneCode);
            if(verRE.getStatusCode() == HttpStatus.OK){//判断验证码
                String encodePassword = SecretEncode.md5(password);
               Integer updateCount = MemberRepository.updatePassword(account,encodePassword);
               if(updateCount == 1){
                   return new ResponseEntity<Member>(HttpStatus.OK);
               }else{
                   String errmsg = "invalid account";
                   logger.info(errmsg);
                   return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
               }
            }else{
            	 return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, "验证码错误");
            }
        }
    }

    @Transactional
    public ResponseEntity<Member> updatePassword(String userId, String password,String newPassword) {

        logger.info("-----Method:\tupdatePassword----");
        logger.info("param:\tuserId:\t"+userId);
        logger.info("param:\tpassword:\t"+password);
        logger.info("param:\tnewPassword:\t"+newPassword);

        //判断旧密码是否错误
        if(StringUtils.isEmpty(password) || StringUtils.isEmpty(newPassword)){
            String errmsg = "password or newPassword invalid";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else if(password.equals(password)){
            String errmsg = "新密码不能与旧密码相同";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        } else{
            String encodePassword = SecretEncode.md5(password);
            String encodeNewPassword = SecretEncode.md5(newPassword);

            Integer updateCount = MemberRepository.updatePassword(userId,encodePassword,encodeNewPassword);
            if(updateCount == 1){
                return new ResponseEntity<Member>(HttpStatus.OK);
            }else{
                String errmsg = "旧密码无效";
                logger.info(errmsg);
                return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
            }
        }
    }

    @Transactional
    public ResponseEntity<Member> updateEmail(String userId, String password,String email) {

        logger.info("-----Method:\tupdateEmail----");
        logger.info("param:\tuserId:\t"+userId);
        logger.info("param:\temail:\t"+email);
        logger.info("param:\tpassword:\t"+password);

        if(StringUtils.isEmpty(password)){
            String errmsg = "password invalid";
            logger.info(errmsg);
            return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else if(StringUtils.isEmpty(email) || !Pattern.matches(".+@.+\\..+",email)){
            String errmsg = "email invalid";
            logger.info(errmsg);
            return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else{
            String encodePassword = SecretEncode.md5(password);

            Integer updateCount = MemberRepository.updateEmail(userId, encodePassword, email);
            if(updateCount == 1){
                return new ResponseEntity<Member>(HttpStatus.OK);
            }else{
                String errmsg = "密码错误";
                logger.info(errmsg);
                return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
            }
        }
    }

    @Transactional
    public ResponseEntity<Member> update(String userId, BankInfo bankInfo, IdInfo idInfo) {

        logger.info("-----Method:\tupdate----");
        logger.info("param:\tuserId:\t"+userId);
        logger.info("param:\tbankInfo:\t"+bankInfo);
        logger.info("param:\tidInfo:\t"+idInfo);

        Member Member = MemberRepository.findOne(userId);

        //TODO 认证身份证和姓名

        //TODO 认证银行卡和身份证

        //TODO 获取银行卡类型

        if(Member == null){
            String errmsg  = "invalid userId";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else if(!StringUtils.isEmpty(Member.getId())){
            String errmsg  = "您已实名认证,无需再次认证";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else{

            Integer updateCount = MemberRepository.update(userId,idInfo.getId(),idInfo.getName(),
                    bankInfo.getBankCardNo(),bankInfo.getBankName());
            // TODO 发送短信通知管理员
            return new ResponseEntity<Member>(HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<Member> updateMoney(String userId,Long money){

        logger.info("-----Method:\tupdateMoney----");

        logger.info("param:\tuserId:\t"+userId);
        logger.info("param:\tmoney:\t"+money);

        if(money == null || money == 0){
            String errmsg = "invalid money";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else{

            if(money > 0){
                MemberRepository.updateBalance(userId,money);
                return new ResponseEntity<Member>(HttpStatus.OK);
            }else{
                Member Member = MemberRepository.findOne(userId);
                if(Member.getBalance() >= ~money+1){
                    MemberRepository.updateBalance(userId,money);
                    return new ResponseEntity<Member>(HttpStatus.OK);
                }else{
                    String errmsg = "账户余额不足";
                    logger.warn(errmsg);
                    return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
                }
            }

        }
    }

    @Override
    public ResponseEntity<Member> updateOuterDiscAccount(String userId, String outerDiscAccount) {

        logger.info("------Method:\tMemberServiceImpl.updateOuterDiscAccount");
        logger.info("userId = [" + userId + "], outerDiscAccount = [" + outerDiscAccount + "]");

        int updateCount = MemberRepository.updateOuterDiscAccount(userId,outerDiscAccount);

        if(updateCount == 1){
            return new ResponseEntity<Member>(HttpStatus.OK);
        }else if(updateCount == 0){
            String errmsg = "invalid userId or outerDiscAccount";
            logger.warn(errmsg);
            return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, errmsg);
        }else{
            logger.error("------Method:\tMemberServiceImpl.updateOuterDiscAccount");
            logger.error("userId = [" + userId + "], outerDiscAccount = [" + outerDiscAccount + "]");
            logger.error("updateCount = " + updateCount);
            
            return new ResponseEntityBuilder<Member>().builder(HttpStatus.BAD_REQUEST, ERROR, "错误");
        }
    }


}
