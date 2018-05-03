/*
package com.gushushu.yanao.usersys.service;

import com.gushushu.yanao.usersys.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;


public interface TransactionService {

    static final String WAIT_CHECK_STATUS = "wait_check";
    static final String SUCCESS_STATUS = "success";
    static final String FAILED_STATUS = "failed";


    ResponseEntity<Transaction> create(String userId,String type,Long money,String status);

    ResponseEntity<Transaction> update(String transactionId,String status,String remark);

    ResponseEntity<Page<Transaction>> findByUserId(String userId,int page,int size);

    ResponseEntity<Page<Transaction>> findByStatusAndType(String status,String type,int page,int size);


}
*/
