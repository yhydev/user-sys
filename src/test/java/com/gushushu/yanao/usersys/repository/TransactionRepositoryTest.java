package com.gushushu.yanao.usersys.repository;

import com.gushushu.yanao.usersys.Application;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.entity.QTransaction;
import com.gushushu.yanao.usersys.entity.Transaction;
import com.gushushu.yanao.usersys.model.FrontMember;
import com.gushushu.yanao.usersys.model.FrontTransaction;
import com.gushushu.yanao.usersys.service.TransactionService;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void findFrontTransaction() {


        Specification<Transaction> specification2 = new Specification<Transaction>() {
            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

              /*  List<Predicate> list = new ArrayList<Predicate>();

                Expression cName2 = root.get("status").as(String.class);
                Predicate predicate2 = criteriaBuilder.equal(cName2, null);
                list.add(predicate2);

                Predicate[] p = new Predicate[list.size()];
                criteriaQuery.where(list.toArray(p));
                return criteriaQuery.getRestriction();*/

              return criteriaBuilder.equal(root.get("status").as(String.class),"failed");

            }

        };


    /*    Pageable pageable = PageRequest.of(0,10, Sort.Direction.DESC,"transactionId");
        Object page = transactionRepository.findFront(specification2,pageable);

        System.out.println(page);
*/


    }


    @Autowired
    JPAQueryFactory jpaQueryFactory;


    @Autowired
    EntityManager entityManager;

    @Test
    public void findAll(){
        QTransaction qTransaction = QTransaction.transaction;

        BooleanExpression expression = qTransaction.status.eq(TransactionService.FAILED_STATUS);

        QBean<FrontTransaction> qBean = Projections.bean(FrontTransaction.class,
                qTransaction.status,
                qTransaction.type,
                qTransaction.money,
                qTransaction.createDate,
                qTransaction.updateDate,
                qTransaction.answer,
                qTransaction.detailId

        );



        Object res = jpaQueryFactory.
                select(qBean)
                .from(qTransaction)
                .where()
                .offset(0)
                .limit(1)
                .orderBy(qTransaction.transactionId.desc())
                .fetchResults();



        System.out.println(res);

    }


}