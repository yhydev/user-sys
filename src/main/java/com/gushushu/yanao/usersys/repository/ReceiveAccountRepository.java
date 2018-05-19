package com.gushushu.yanao.usersys.repository;

import com.gushushu.yanao.usersys.entity.ReceiveAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReceiveAccountRepository extends JpaRepository<ReceiveAccount,String> {

    boolean existsByBankNo(String bankNo);

    @Query("select t.receiveAccountId from ReceiveAccount t where t.bankNo = :bankNo")
    String findIdByBankNo(@Param("bankNo") String bankNo);

    ReceiveAccount findByReceiveAccountId(String id);



}
