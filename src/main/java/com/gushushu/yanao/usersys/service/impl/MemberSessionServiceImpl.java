package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.entity.MemberSession;
import com.gushushu.yanao.usersys.model.FrontMemberSession;
import com.gushushu.yanao.usersys.repository.MemberRepository;
import com.gushushu.yanao.usersys.repository.MemberSessionRepository;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;


@Service
public class MemberSessionServiceImpl implements MemberSessionService {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private MemberSessionRepository memberSessionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public ResponseEntity findMemberId(String token){

        logger.info("token = [" + token + "]");

        ResponseEntity responseEntity = null;

        String memberId = memberSessionRepository.findMemberId(token);

        if (memberId == null){
            responseEntity = ResponseEntityBuilder.failed("会话到期.");
        }else{
            responseEntity = ResponseEntityBuilder.success(memberId);
        }

        logger.info("responseEntity = " + responseEntity);

        return responseEntity;
    }

    @Override
    public ResponseEntity<ResponseBody<FrontMemberSession>> findSession(String token) {

        logger.info("token = [" + token + "]");

        ResponseEntity response = null;

        FrontMemberSession frontMemberSession = memberSessionRepository.findSession(token);

        if (frontMemberSession == null){
            response = ResponseEntityBuilder.failed("会话超时");
        }else{
            response = ResponseEntityBuilder.<FrontMemberSession>success(frontMemberSession);
        }




        logger.info("response = " + response);

        return response;
    }

    @Override
    public ResponseEntity findMember(String token) {

        logger.info("token = " + token);

        ResponseEntity responseEntity = null;

        MemberSession memberSession = memberSessionRepository.findByToken(token);

        if (memberSession == null){
            responseEntity = ResponseEntityBuilder.failed("会话超时");
        }else{
            responseEntity = ResponseEntityBuilder.<Member>success(memberSession.getMember());
        }

        logger.info("responseEntity = " + responseEntity);

        return responseEntity;
    }



    @Override
    @Transactional
    public ResponseEntity saveSession(String memberId) {
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
