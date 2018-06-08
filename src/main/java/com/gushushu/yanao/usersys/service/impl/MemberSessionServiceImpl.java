package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.entity.MemberSession;
import com.gushushu.yanao.usersys.entity.QMemberSession;
import com.gushushu.yanao.usersys.model.FrontMemberSession;
import com.gushushu.yanao.usersys.repository.MemberRepository;
import com.gushushu.yanao.usersys.repository.MemberSessionRepository;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.util.CollectionUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Service
public class MemberSessionServiceImpl implements MemberSessionService {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private MemberSessionRepository memberSessionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;


    @Override
    public <T> ResponseEntity<ResponseBody<T>> findOne(FindOneParam<T> findOneParam) {

        System.out.println("findOneParam = [" + findOneParam + "]");

        ResponseEntity<ResponseBody<T>> response = null;
        String errmsg = null;
        List<Predicate> predicates  = new ArrayList<>();

        if(!StringUtils.isEmpty(findOneParam.getEqToken())){
            Predicate predicate = QMemberSession.memberSession.token.eq(findOneParam.getEqToken());
            predicates.add(predicate);
        }

        Predicate predicate[] = new Predicate[predicates.size()];
        predicates.toArray(predicate);

        T result = jpaQueryFactory.select(findOneParam.getSelect()).from(QMemberSession.memberSession).where(predicate).fetchOne();

        if(result == null){
            response = ResponseEntityBuilder.failed("token 过期或无效");
        }else{
            response = ResponseEntityBuilder.success(result);
        }

        logger.info("response = " + response);

        return response;
    }

    @Override
    @Transactional
    public ResponseEntity create(String memberId) {
        logger.info("memberId = [" + memberId + "]");

        MemberSession memberSession = new MemberSession();

        String token = HexUtils.toHexString(RandomUtils.nextBytes(16));
        Member member = new Member();
        member.setMemberId(memberId);
        memberSession.setCreateTime(new Date());
        memberSession.setMember(member);
        memberSession.setToken(token);

        memberSessionRepository.save(memberSession);

        String account = memberRepository.findAccount(memberId);



        FrontMemberSession frontMemberSession = new FrontMemberSession(memberId,token,account);

        return ResponseEntityBuilder.success(frontMemberSession);
    }





}
