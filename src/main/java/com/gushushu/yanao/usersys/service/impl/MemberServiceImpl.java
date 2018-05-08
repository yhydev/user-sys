package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.entity.IdentifyingCode;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.entity.MemberSession;
import com.gushushu.yanao.usersys.model.BackMember;
import com.gushushu.yanao.usersys.repository.MemberRepository;
import com.gushushu.yanao.usersys.service.MemberService;
import com.gushushu.yanao.usersys.service.IdentifyingCodeService;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    final static Logger logger = Logger.getLogger(MemberServiceImpl.class);




    //用户token 超时时间(单位秒)
    final static int USER_TOKEN_TIMEOUT = 1800;


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberSessionService memberSessionService;

    @Autowired
    private IdentifyingCodeService identifyingCodeService;


    @Override
    @Transactional
    public ResponseEntity<ResponseBody<String>> setInnerDiscAccount(SetInnerDiscAccountParam setInnerDiscAccountParam) {

        System.out.println("setInnerDiscAccountParam = [" + setInnerDiscAccountParam + "]");

        ResponseEntity<ResponseBody<String>> response = null;

        Member member = memberRepository.findByMemberId(setInnerDiscAccountParam.getAccountId());

        if(member != null){

            member.setSetInnerDiscTime(new Date());
            member.setInnerDiscAccount(setInnerDiscAccountParam.getInnerDiscAccount());

            memberRepository.save(member);
            response = ResponseEntityBuilder.success(null);

        }else{
            response = ResponseEntityBuilder.failed("unknown member");
        }

        System.out.println("response = " + response);

        return response;
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseBody<MemberSession>> register(RegisterParam param) {

        logger.info("param = [" + param + "]");

        ResponseEntity<ResponseBody<MemberSession>> response = null;

        //验证码 验证
        IdentifyingCodeService.ValidateParam validateParam = new IdentifyingCodeService.ValidateParam();
        validateParam.type = "register";
        validateParam.phone = param.getAccount();
        validateParam.code = param.getPhoneCode();

        ResponseEntity<ResponseBody<IdentifyingCode>> responseEntity = identifyingCodeService.validate(validateParam);

        //验证码是否正确
        if(responseEntity.getBody().isSuccess()){

            Long accountCount = memberRepository.countByAccount(param.getAccount());

            if(accountCount != 0){

                //验证码错误
                String errmsg = "此手机号已被注册.";
                response = ResponseEntityBuilder.failed(errmsg);
            }else{

                //验证码正确

                Member member = new Member();
                member.setCreateDate(new Date());
                member.setPassword(param.getPassword());
                member.setAccount(param.getAccount());
                member.setBalance(0L);
                memberRepository.save(member);

                memberSessionService.saveSession(member.getMemberId());

            }
        }else{
            response = ResponseEntityBuilder.failed(responseEntity.getBody().getMessage());
        }

        logger.info("response = " + response);

        return response;
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseBody<MemberSession>> login(LoginParam loginParam) {

        logger.info("loginParam = [" + loginParam + "]");

        ResponseEntity<ResponseBody<MemberSession>> response = null;

        String memberId = memberRepository.findId(loginParam.getAccount(),loginParam.getPassword());

        if(memberId == null){
            response = ResponseEntityBuilder.failed("账号或密码错误.");
        }else{
            response = memberSessionService.saveSession(memberId);
        }

        logger.info("response = " + response);

        return response;
    }

    @Override
    public ResponseEntity<ResponseBody<Page<BackMember>>> search(final SearchParam param, Pageable pageable) {

        logger.info("param = [" + param + "], pageable = [" + pageable + "]");

        ResponseEntity<ResponseBody<Page<BackMember>>> response;

        Specification<Member> specification = new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<Predicate>();

                if(param.getWaitSetInnerDisc() == true){
                    Expression cName = root.get("idCard").as(String.class);
                    Predicate predicate = criteriaBuilder.isNotNull(cName);
                    list.add(predicate);


                    Expression cName2 = root.get("innerDiscAccount").as(String.class);
                    Predicate predicate2 = criteriaBuilder.equal(cName2,null);
                    list.add(predicate);
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };


        Page ret = memberRepository.findAll(specification,pageable);

        response = ResponseEntityBuilder.success(ret);

        logger.info("response = " + response);

        return response;
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseBody> realName(RealNameParam param) {

        logger.info("param = [" + param + "]");

        ResponseEntity response = null;

        ResponseEntity<ResponseBody<Member>> rep = memberSessionService.findMember(param.getToken());

        if(rep.getBody().isSuccess()){
            Member member = rep.getBody().getData();
            member.setIdCard(param.getIdCard());
            member.setName(param.getName());
            member.setBankCard(param.getBankCard());
            member.setIdCardBehindUrl(param.getIdCardBehindUrl());
            member.setIdCardFrontUrl(param.getIdCardFrontUrl());
            member.setPhoneNumber(param.getPhoneNumber());
            member.setRealNameTime(new Date());

            memberRepository.save(member);

            response = ResponseEntityBuilder.success(null);
        }else{
            response = ResponseEntityBuilder.failed(rep.getBody().getMessage());
        }

        logger.info("response = " + response);

        return response;
    }




    /*

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
*/



}
