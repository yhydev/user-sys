package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.entity.IdentifyingCode;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.entity.MemberSession;
import com.gushushu.yanao.usersys.model.BackMember;
import com.gushushu.yanao.usersys.model.FrontMember;
import com.gushushu.yanao.usersys.model.FrontMemberSession;
import com.gushushu.yanao.usersys.repository.MemberRepository;
import com.gushushu.yanao.usersys.service.MemberService;
import com.gushushu.yanao.usersys.service.IdentifyingCodeService;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import com.querydsl.core.QueryResults;
import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
import static com.gushushu.yanao.usersys.entity.QMember.member;
@Service
public class MemberServiceImpl implements MemberService {

    final static Logger logger = Logger.getLogger(MemberServiceImpl.class);

    public final static String USER_TYPE = "user";
    public final static String MANAGER_TYPE = "manager";

    public static QBean<BackMember> backMemberQBean = Projections.bean(
            BackMember.class,
            member.memberId,
            member.account,//账户 (手机号)
            member.createDate,// 创建日期
            member.applyForOpenAccount,//是否申请开户
            member.openAccount,//是否已经开户
            member.name, //姓名
            member.idCard, //身份证
            member.idCardFrontUrl, //身份证正面
            member.idCardBehindUrl, //身份证反面
            member.bankCard,//银行卡
            member.phoneNumber,//银行卡预留手机号
            member.applyForOpenAccountDate,//实名时间
            member.innerDiscAccount,//内盘账户
            member.setInnerDiscDate//设置内盘账户日期
    );



    //用户token 超时时间(单位秒)
    final static int USER_TOKEN_TIMEOUT = 1800;


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberSessionService memberSessionService;

    @Autowired
    private IdentifyingCodeService identifyingCodeService;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public ResponseEntity<ResponseBody<FrontMember>> getFrontMember(String token) {

        logger.info("token = [" + token + "]");

        ResponseEntity<ResponseBody<FrontMember>> response = null;

        ResponseEntity<ResponseBody<String>> findMemberIdResponse = memberSessionService.findMemberId(token);
        if(findMemberIdResponse.getBody().isSuccess()){
            FrontMember frontMember = memberRepository.findFront(findMemberIdResponse.getBody().getData());
            response = ResponseEntityBuilder.success(frontMember);
        }else{
            response = ResponseEntityBuilder.failed(findMemberIdResponse.getBody().getMessage());
        }

        logger.info("response = " + response);

        return response;
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseBody<String>> setInnerDiscAccount(SetInnerDiscAccountParam setInnerDiscAccountParam) {

        logger.info("setInnerDiscAccountParam = [" + setInnerDiscAccountParam + "]");

        ResponseEntity<ResponseBody<String>> response = null;

        Member member = memberRepository.findByMemberId(setInnerDiscAccountParam.getAccountId());

        if(member != null){

            member.setApplyForOpenAccountDate(new Date());
            member.setInnerDiscAccount(setInnerDiscAccountParam.getInnerDiscAccount());
            member.setOpenAccount(true);
            memberRepository.save(member);
            response = ResponseEntityBuilder.success(null);

        }else{
            response = ResponseEntityBuilder.failed("unknown member");
        }

        logger.info("response = " + response);

        return response;
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseBody<FrontMemberSession>> create(CreateParam createParam) {

        logger.info("createParam = [" + createParam + "]");

        ResponseEntity<ResponseBody<FrontMemberSession>> response = null;
        String errmsg = null;
        Long accountCount = memberRepository.countByAccount(createParam.getAccount());

        //手机号是否存在
        if(accountCount != 0){
            errmsg = "此手机号已被注册.";
        }else{
            Member member = new Member();
            member.setCreateDate(new Date());
            member.setPassword(createParam.getPassword());
            member.setAccount(createParam.getAccount());
            member.setBalance(0L);
            member.setOpenAccount(false);
            member.setApplyForOpenAccount(false);
            member.setType(createParam.getType());
            memberRepository.save(member);

            response = memberSessionService.saveSession(member.getMemberId());
        }


        if(errmsg != null){
            response = ResponseEntityBuilder.failed(errmsg);
        }


        logger.info("response = " + response);

        return response;
    }



    @Override
    @Transactional
    public ResponseEntity<ResponseBody<FrontMemberSession>> login(LoginParam loginParam) {

        logger.info("loginParam = [" + loginParam + "]");

        ResponseEntity<ResponseBody<FrontMemberSession>> response = null;

        String memberId = memberRepository.findId(loginParam.getAccount(),loginParam.getPassword());

        if(memberId == null){
            response = ResponseEntityBuilder.failed("账号或密码错误.");
        }else{
            response = memberSessionService.saveSession(memberId);
        }

        logger.info("response = " + response);

        return response;
    }

    private List<Predicate> generatePredicate(SearchParam searchParam){

        List<Predicate> predicates  = new ArrayList<>();

        if(searchParam.getApplyForOpenAccount() != null){
            Predicate predicate = member.applyForOpenAccount.eq(searchParam.getApplyForOpenAccount());
            predicates.add(predicate);
        }

        if(searchParam.getOpenAccount() != null){
            Predicate predicate = member.openAccount.eq(searchParam.getOpenAccount());
            predicates.add(predicate);
        }


        return predicates;
    }

    @Override
    public <T> ResponseEntity<ResponseBody<QueryResults<T>>> search(SearchParam<T> searchParam) {

        System.out.println("searchParam = " + searchParam);

        ResponseEntity<ResponseBody<QueryResults<T>>> response;

        List<Predicate> predicates = generatePredicate(searchParam);
        Predicate[] predicateArray = new Predicate[predicates.size()];
        predicates.toArray(predicateArray);

        QueryResults<T> queryResults = jpaQueryFactory.select(searchParam.getResultBean())
                .from(member)
                .where(predicateArray)
                .offset(searchParam.getPage())
                .limit(searchParam.getSize())
                .orderBy(member.memberId.asc())
                .fetchResults();

        response = ResponseEntityBuilder.<QueryResults<T>>success(queryResults);

        logger.info("response = " + response);

        return response;
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseBody> applyForAccount(ApplyForAccountParam applyForAccountParam){

        System.out.println("applyForAccountParam = [" + applyForAccountParam + "]");

        ResponseEntity response = null;

        ResponseEntity<ResponseBody<Member>> rep = memberSessionService.findMember(applyForAccountParam.getToken());

        if(rep.getBody().isSuccess()){

            Member member = rep.getBody().getData();

            if(member.getApplyForOpenAccount()!=null && member.getApplyForOpenAccount()){
                response = ResponseEntityBuilder.failed("您已经申请开户");
            }else{
                member.setIdCard(applyForAccountParam.getIdCard());
                member.setName(applyForAccountParam.getName());
                member.setBankCard(applyForAccountParam.getBankCard());
                member.setIdCardBehindUrl(applyForAccountParam.getIdCardBehindUrl());
                member.setIdCardFrontUrl(applyForAccountParam.getIdCardFrontUrl());
                member.setPhoneNumber(applyForAccountParam.getPhoneNumber());
                member.setApplyForOpenAccountDate(new Date());
                member.setApplyForOpenAccount(true);

                memberRepository.save(member);

                response = ResponseEntityBuilder.success(null);
            }

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
