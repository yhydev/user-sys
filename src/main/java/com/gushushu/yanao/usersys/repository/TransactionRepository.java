package com.gushushu.yanao.usersys.repository;

import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.entity.Transaction;
import com.gushushu.yanao.usersys.model.FrontTransaction;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import javax.persistence.FieldResult;


public interface TransactionRepository extends JpaRepository<Transaction,String>,QuerydslPredicateExecutor<Transaction> /*,JpaSpecificationExecutor<Transaction>*/ {

    Transaction findByTransactionId(String id);

/*
    //@FieldResult()
   @Query("select new com.gushushu.yanao.usersys.model.FrontTransaction(t.status, t.type, t.money, t.createDate, t.updateDate, t.answer, t.detailId) from Transaction t")
    Page<FrontTransaction> findFront(Specification var1, Pageable var2);
*/
    // Page<FrontTransaction> findAll(Specification<Transaction> specification, Pageable pageRequest);

    /*

    Page<Transaction> findByUserId(String userId, Pageable pageable);

    Page<Transaction> findByStatusAndType(String status,String type, Pageable pageable);

    @Modifying
    @Query("select sum(money) from Transaction where userId = :userId and status = :status and type = :type")
    Long sumMoneyByUserIdAndStatusAndType(@Param("userId")String userId,@Param("status") String status,@Param("type") String type);

    @Modifying
    @Query("update Transaction set status = :status,remark = :remark where transactionId = :transactionId")
    Integer updateStatusByTransactionId(@Param("transactionId") String transactionId,@Param("status") String status,@Param("remark") String remark);
*/

    //Integer updateStatusByTransactionIdAndStatus();

}
