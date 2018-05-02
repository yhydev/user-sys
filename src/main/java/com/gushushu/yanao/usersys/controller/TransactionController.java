package com.gushushu.yanao.usersys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gushushu.yanao.usersys.config.AppConstant;
import com.gushushu.yanao.usersys.entity.Transaction;
import com.gushushu.yanao.usersys.service.TransactionService;

/**
 * 交易模块Controller
 * @author 57403
 *
 */
@RestController
@RequestMapping("tranasction")
public class TransactionController implements AppConstant{

	@Autowired
	private TransactionService transactionService;
	
	/**
	 * 充值
	 * @param userId
	 * @param money
	 * @return
	 */
	@PostMapping("create/recharge")
	public  ResponseEntity<Transaction> createForRecharge(String userId, Long money){
		return transactionService.create(userId, TRANSACTION_TYPE_RECHARGE, money, STRING_WAIT_PAY);
	}
	
	/**
	 * 入金
	 * @param userId
	 * @param money
	 * @return
	 */
	@PostMapping("create/inmoney")
	public  ResponseEntity<Transaction> createForInmoney(String userId, Long money){
		return transactionService.create(userId, TRANSACTION_TYPE_INMONEY, money, STRING_WAIT_CHECK);
	}
	
	/**
	 * 出金
	 * @param userId
	 * @param money
	 * @return
	 */
	@PostMapping("create/outmoney")
	public  ResponseEntity<Transaction> createForOutmoney(String userId, Long money){
		return transactionService.create(userId, TRANSACTION_TYPE_OUTMONEY, money, STRING_WAIT_CHECK);
	}
	
	/**
	 * 提现
	 * @param userId
	 * @param money
	 * @return
	 */
	@PostMapping("create/withdrawals")
	public  ResponseEntity<Transaction> createForWithdrawals(String userId, Long money){
		return transactionService.create(userId, TRANSACTION_TYPE_WITHDRAWALS, money, STRING_WAIT_CHECK);
	}
	
	/**
	 * 审核
	 * @param transactionId
	 * @param status
	 * @param remark
	 * @return
	 */
	@PostMapping("update")
	public  ResponseEntity<Transaction> update(String transactionId,String status,String remark){
		return transactionService.update(transactionId, status, remark);
	}
	
	/**
	 * 根据用户id查询交易记录
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping("list/userid")
	public  ResponseEntity<Page<Transaction>> findByUserId(String userId,int page,int size){
		return transactionService.findByUserId(userId, page, size);
	}
	
	/**
	 * 根据状态和类型查询交易记录
	 * @param status
	 * @param type
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping("list/statusAndType")
	public  ResponseEntity<Page<Transaction>> findByStatusAndType(String status, String type,int page,int size){
		return transactionService.findByStatusAndType(status, type, page, size);
	}
}
