package com.gushushu.yanao.usersys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gushushu.yanao.usersys.entity.Transaction;
import com.gushushu.yanao.usersys.service.TransactionService;

@RestController
@RequestMapping("tranasction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@PostMapping("create")
	public  ResponseEntity<Transaction> create(String userId, String type, Long money, String status){
		return transactionService.create(userId, type, money, status);
	}
	
	@PostMapping("update")
	public  ResponseEntity<Transaction> update(String transactionId,String status,String remark){
		return transactionService.update(transactionId, status, remark);
	}
	
	@PostMapping("list/userid")
	public  ResponseEntity<Page<Transaction>> findByUserId(String userId,int page,int size){
		return transactionService.findByUserId(userId, page, size);
	}
	
	@PostMapping("list/statusAndType")
	public  ResponseEntity<Page<Transaction>> findByStatusAndType(String status, String type,int page,int size){
		return transactionService.findByStatusAndType(status, type, page, size);
	}
}
