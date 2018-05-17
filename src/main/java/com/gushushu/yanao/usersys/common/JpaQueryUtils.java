package com.gushushu.yanao.usersys.common;

import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;

public class JpaQueryUtils {

    public static <ResultBeanType,Q extends QueryBase<Q>> Q generateQuery(JPAQueryFactory jpaQueryFactory,
                                                           QBean<ResultBeanType> qBean,
                                                           EntityPathBase entityPathBase,
                                                           List<Predicate> where){


        Predicate[] predicates = new Predicate[where.size()];
        where.toArray(predicates);
/*
        Path[] pathArr = new Path[paths.size()];
        paths.toArray(pathArr);
*/


    //    QBean<ResultBeanType> qBean = Projections.bean(resultBeanType,path);


     /*   return  jpaQueryFactory.
                select(qBean)
                .from(entityPathBase)
                .where(predicates);
                .offset(searchFrontParam.getPage())
                .limit(searchFrontParam.getSize())
                .orderBy(qTransaction.transactionId.desc())
                .fetchResults();*/

     jpaQueryFactory.
                select(qBean)
                .from(entityPathBase)
                .where(predicates);




        return null;
    }



}
