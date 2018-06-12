package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.Application;
import com.gushushu.yanao.usersys.common.QBeans;
import com.gushushu.yanao.usersys.entity.QMember;
import com.querydsl.core.FetchableQuery;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class QueryTest {


    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Test
    public void test(){


        QBean qBean = Projections.bean(
                QMember.member.memberId,
                QMember.member.type
        );


         List list = jpaQueryFactory.select(qBean).from(QMember.member).fetch();
        System.out.println(list);



    }




}
