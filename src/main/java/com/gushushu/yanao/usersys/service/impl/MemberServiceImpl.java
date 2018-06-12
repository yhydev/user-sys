package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.common.QBeans;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.entity.QMember;
import com.gushushu.yanao.usersys.model.FrontMemberSession;
import com.gushushu.yanao.usersys.model.QueryData;
import com.gushushu.yanao.usersys.repository.MemberRepository;
import com.gushushu.yanao.usersys.service.MemberService;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import com.gushushu.yanao.usersys.service.RelationService;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.gushushu.yanao.usersys.entity.QMember.member;
@Service
public class MemberServiceImpl implements MemberService {

    final static Logger logger = Logger.getLogger(MemberServiceImpl.class);

    public  static class MemberType {
        public final static String USER_TYPE = "user";
        public final static String MANAGER_TYPE = "manager";
        public final static String PROXY_TYPE = "proxy";

    }

    public  static class MemberOpenAccountStatus{
        public static String APPLY_FOR = "apply_for";
        public static String NOT_APPLY_FOR = "not_apply_for";
        public static String OPEN_ACCOUNT = "open_account";
        public static String REJECT = "reject";

    }

    //用户token 超时时间(单位秒)
    final static int USER_TOKEN_TIMEOUT = 1800;


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberSessionService memberSessionService;

    @Autowired
    private RelationService relationService;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;


    @Override
    public String findMemberId(String account, String password) {


        String memberId = jpaQueryFactory.select(member.memberId).where(
                member.account.eq(account),
                member.password.eq(password)
        ).fetchOne();


        return memberId;
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseBody<String>> setInnerDiscAccount(SetInnerDiscAccountParam setInnerDiscAccountParam) {

        logger.info("setInnerDiscAccountParam = [" + setInnerDiscAccountParam + "]");

        ResponseEntity<ResponseBody<String>> response = null;
        String errmsg = null;

        Member member = memberRepository.findByMemberId(setInnerDiscAccountParam.getMemberId());

        if(member == null){
            errmsg = "未知 member";
        }else if(!MemberOpenAccountStatus.APPLY_FOR.equals(member.getOpenAccountStatus())){
            errmsg = "用户未实名";
        }else{
            member.setApplyForOpenAccountDate(new Date());
            member.setInnerDiscAccount(setInnerDiscAccountParam.getInnerDiscAccount());
            member.setOpenAccountStatus(MemberOpenAccountStatus.OPEN_ACCOUNT);
            member.setSetInnerDiscDate(new Date());
            memberRepository.save(member);

            response = ResponseEntityBuilder.success(member.getMemberId());
        }

        if(errmsg != null){
            response = ResponseEntityBuilder.failed(errmsg);
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

        //手机号是否存在(如果手机号是 null 或 空字符 则不验证)
        if(!StringUtils.isEmpty(createParam.getAccount()) && memberRepository.countByAccount(createParam.getAccount()) != 0){
            errmsg = "此手机号已被注册.";
        }else{

            //创建用户
            Member member = new Member();
            member.setCreateDate(new Date());
            member.setPassword(createParam.getPassword());
            member.setAccount(createParam.getAccount());
            member.setBalance(0L);
            member.setOpenAccountStatus(MemberOpenAccountStatus.NOT_APPLY_FOR);
            member.setType(createParam.getType());

            memberRepository.save(member);

            //创建用户与代理之间的关系
            RelationService.CreateParam relationCreateParam = new RelationService.CreateParam();
            relationCreateParam.setProxyId(createParam.getProxyId());
            relationCreateParam.setMemberId(member.getMemberId());
            relationService.create(relationCreateParam);

            response = memberSessionService.create(member.getMemberId());

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
            response = memberSessionService.create(memberId);
        }

        logger.info("response = " + response);

        return response;
    }

    private List<Predicate> generatePredicate(SearchParam searchParam){

        List<Predicate> predicates  = new ArrayList<>();

        if(!StringUtils.isEmpty(searchParam.getOpenAccountStatus())){
            Predicate predicate = member.openAccountStatus.eq(searchParam.getOpenAccountStatus());
            predicates.add(predicate);
        }

        if(!StringUtils.isEmpty(searchParam.getType())){
            Predicate predicate = member.type.eq(searchParam.getType());
            predicates.add(predicate);
        }

        return predicates;
    }


    public ResponseEntity<ResponseBody<Member>> findOne(String memberId){

        System.out.println("memberId = [" + memberId + "]");
        ResponseEntity response = null;
        Member member = memberRepository.findByMemberId(memberId);

        if(member == null){
            response = ResponseEntityBuilder.failed("无效的用户");
        }else{
            response = ResponseEntityBuilder.success(member);
        }
        System.out.println("response = " + response);
        return response;
    }


    @Override
    public <T> ResponseEntity<ResponseBody<QueryData<T>>> search(SearchParam<T> searchParam) {

        System.out.println("searchParam = " + searchParam);

        ResponseEntity<ResponseBody<QueryData<T>>> response = null;

        List<Predicate> predicates = generatePredicate(searchParam);
        Predicate[] predicateArray = new Predicate[predicates.size()];
        predicates.toArray(predicateArray);

        QueryResults<T> queryResults = jpaQueryFactory.select(searchParam.getResultBean())
                .from(member)
                .where(predicateArray)
                .offset(searchParam.getSize() * searchParam.getPage())
                .limit(searchParam.getSize())
                .orderBy(member.memberId.asc())
                .fetchResults();
        QueryData<T> queryData = new QueryData<T>(queryResults.getResults()
                ,searchParam.getPage(),searchParam.getSize(),queryResults.getTotal());
        response = ResponseEntityBuilder.<QueryData<T>>success(queryData);

        logger.info("response = " + response);

        return response;
    }






    @Override
    @Transactional
    public ResponseEntity<ResponseBody> applyForAccount(ApplyForAccountParam applyForAccountParam){

        System.out.println("applyForAccountParam = [" + applyForAccountParam + "]");
        String errmsg = null;
        ResponseEntity response = null;

        MemberSessionService.FindOneParam findOneParam =
                new MemberSessionService.FindOneParam(QBeans.MEMBER_OPEN_ACCOUNT_STATUS);
        findOneParam.setEqToken(applyForAccountParam.getToken());
        ResponseEntity<ResponseBody<QBeans.MemberOpenAccountModel>> rep = memberSessionService.findOne(findOneParam);

        if(rep.getBody().isSuccess()){

            QBeans.MemberOpenAccountModel openAccountModel = rep.getBody().getData();
            if(MemberOpenAccountStatus.OPEN_ACCOUNT.equals(openAccountModel.getOpenAccountStatus())){
                errmsg = "您已经申请开户";
            }else{
                Long idCardCount = memberRepository.countByIdCardAndStatus(applyForAccountParam.getIdCard(),MemberOpenAccountStatus.APPLY_FOR);
                idCardCount += memberRepository.countByIdCardAndStatus(applyForAccountParam.getIdCard(),MemberOpenAccountStatus.OPEN_ACCOUNT);

                //判断此身份证是否开户
                if(idCardCount > 0){
                    errmsg = "此身份证号已开户,请更换身份证号码";
                }else{

                    Member member = memberRepository.findByMemberId(openAccountModel.getMemberId());

                    member.setIdCard(applyForAccountParam.getIdCard());
                    member.setName(applyForAccountParam.getName());
                    member.setBankCard(applyForAccountParam.getBankCard());
                    member.setIdCardBehindUrl(applyForAccountParam.getIdCardBehindUrl());
                    member.setIdCardFrontUrl(applyForAccountParam.getIdCardFrontUrl());
                    member.setPhoneNumber(applyForAccountParam.getPhoneNumber());
                    member.setApplyForOpenAccountDate(new Date());
                    member.setOpenAccountStatus(MemberOpenAccountStatus.APPLY_FOR);

                    memberRepository.save(member);

                    response = ResponseEntityBuilder.success(openAccountModel.getMemberId());
                }
            }

        }else{
            errmsg = rep.getBody().getMessage();
        }

        if(errmsg != null){
            response = ResponseEntityBuilder.failed(errmsg);
        }

        logger.info("response = " + response);

        return response;
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseBody<String>> update(UpdateOneParam updateOneParam) {

        System.out.println("updateOneParam = [" + updateOneParam + "]");

        JPAUpdateClause jpaUpdateClause = jpaQueryFactory.update(QMember.member);
        ResponseEntity<ResponseBody<String>> response = null;
        String errmsg = null;

        if(!StringUtils.isEmpty(updateOneParam.getOpenAccountStatus())){
            jpaUpdateClause.set(QMember.member.openAccountStatus,updateOneParam.getOpenAccountStatus());
        }

        if(jpaUpdateClause.isEmpty()){
            errmsg = "无效的更新数据";
        }else{
            Predicate[] predicates = new Predicate[updateOneParam.getWhere().size()];
            updateOneParam.getWhere().toArray(predicates);
            jpaUpdateClause.where(predicates);
            Long upCount = jpaUpdateClause.execute();
            if(upCount != 1){
                //TODO 事务回滚
                errmsg = "无效的更新 update count " + upCount;
            }else{
                response = ResponseEntityBuilder.success(updateOneParam.getEqMemberId());
            }
        }


        if(errmsg != null){
            response = ResponseEntityBuilder.failed(errmsg);
        }

        System.out.println("response = " + response);

        return response;
    }




}
