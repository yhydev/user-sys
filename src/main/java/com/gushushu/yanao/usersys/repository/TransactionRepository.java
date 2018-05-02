package com.gushushu.yanao.usersys.repository;

import com.gushushu.yanao.usersys.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction,String> {
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
