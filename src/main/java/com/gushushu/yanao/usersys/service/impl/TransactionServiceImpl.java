package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.config.AppConstant;
import com.gushushu.yanao.usersys.entity.Transaction;
import com.gushushu.yanao.usersys.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class TransactionServiceImpl implements TransactionService,AppConstant {



    @Override
    public ResponseEntity<Transaction> create(String userId, String type, Long money, String status) {
        return null;
    }

    @Override
    public ResponseEntity update(String transactionId,String status) {
        return null;
    }

    @Override
    public ResponseEntity<Page<Transaction>> findByUserId(String userId) {
        return null;
    }

    @Override
    public ResponseEntity<Page<Transaction>> findByStatusAndType(String status, String type) {
        return null;
    }

}
