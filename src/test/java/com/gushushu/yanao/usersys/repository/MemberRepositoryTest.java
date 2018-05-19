package com.gushushu.yanao.usersys.repository;

import com.gushushu.yanao.usersys.Application;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void findAll() {


        Specification<Member> specification = new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                root.get("status").as(String.class).equals(TransactionService.WAIT_CHECK_STATUS);

                return null;
            }
        };

       // memberRepository.findAll(specification, PageRequest.of(0,10, Sort.Direction.DESC,"memberId"));


    }
}