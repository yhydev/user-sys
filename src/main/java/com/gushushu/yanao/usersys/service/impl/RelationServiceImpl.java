package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.common.PageParam;
import com.gushushu.yanao.usersys.common.QBeans;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.entity.QRelation;
import com.gushushu.yanao.usersys.entity.Relation;
import com.gushushu.yanao.usersys.model.BackMember;
import com.gushushu.yanao.usersys.model.QueryData;
import com.gushushu.yanao.usersys.repository.MemberRepository;
import com.gushushu.yanao.usersys.repository.RelationRepository;
import com.gushushu.yanao.usersys.service.RelationService;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.beans.Beans;

@Service
public class RelationServiceImpl implements RelationService {
    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RelationRepository relationRepository;

    @Override
    @Transactional
    public ResponseEntity<ResponseBody> create(CreateParam createParam) {

        logger.info("createParam = [" + createParam + "]");
        ResponseEntity response = null;
        String errmsg = null;

        if(StringUtils.isEmpty(createParam.getProxyId()) || StringUtils.isEmpty(createParam.getMemberId())){
            errmsg = "无效的id";
        }else{
            Long proxyCount = memberRepository.countByMemberId(createParam.getProxyId());
            Long memberCount = memberRepository.countByMemberId(createParam.getMemberId());

            if(proxyCount != 1 || memberCount != 1){
                errmsg = "id 不存在";
            }else{
                Relation relation = new Relation();
                relation.setMember(new Member(createParam.getMemberId()));
                relation.setProxyMember(new Member(createParam.getProxyId()));
                relationRepository.save(relation);
                response = ResponseEntityBuilder.success(relation.getRelationId());
            }

        }

        if (errmsg != null){
            response = ResponseEntityBuilder.failed(errmsg);
        }

        return response;
    }

    @Override
    public ResponseEntity<ResponseBody<QueryData<BackMember>>> find(String proxyMemberId, PageParam page) {


        logger.info("proxyMemberId = [" + proxyMemberId + "], page = [" + page + "]");

        ResponseEntity<ResponseBody<QueryData<BackMember>>> response = null;

        QueryResults<BackMember> queryResults =  jpaQueryFactory
                .select(QBeans.MEMBER_MANAGER)
                .from(QRelation.relation)
                .where(
                    QRelation.relation.proxyMember.memberId.eq(proxyMemberId)
                ).limit(page.getSize())
                .offset(page.getPage() * page.getSize()).
                orderBy(QRelation.relation.member.createDate.desc())
                .fetchResults();

        QueryData queryData = new QueryData(queryResults.getResults()
                ,page.getPage(),page.getSize(),queryResults.getTotal());
        response = ResponseEntityBuilder.<QueryData<BackMember>>success(queryData);
        logger.info("response = " + response);
        return response;
    }


}
