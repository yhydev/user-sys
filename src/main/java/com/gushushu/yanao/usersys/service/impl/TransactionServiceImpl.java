/*
package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.config.AppConstant;
import com.gushushu.yanao.usersys.entity.Transaction;
import com.gushushu.yanao.usersys.entity.UserInfo;
import com.gushushu.yanao.usersys.repository.TransactionRepository;
import com.gushushu.yanao.usersys.service.TransactionService;
import com.gushushu.yanao.usersys.service.UserInfoService;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

*
 * 交易模块Service
 * @author 57403
 *


@Service
public class TransactionServiceImpl implements TransactionService,AppConstant {

	final static Logger logger = Logger.getLogger(UserInfoServiceImpl.class);
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Transactional
    @Override
    public ResponseEntity<Transaction> create(String userId, String type, Long money, String status) {
    	logger.info("-----TransactionServiceImpl/create-----");
    	logger.info("userID="+userId);
    	logger.info("type="+type);
    	logger.info("money="+money);
    	logger.info("status="+status);
    	
    	if(STRING_SUCCESS.equals(status) || money < 0){
    		ResponseEntity<UserInfo> RE = userInfoService.updateMoney(userId, money);
    		if(RE.getStatusCode() == HttpStatus.OK){
    			Transaction tran = new Transaction();
            	tran.setCreateDate(new Date());
            	tran.setMoney(money);
            	tran.setStatus(status);
            	tran.setType(type);
            	tran.setUserId(userId);
            	transactionRepository.save(tran);
            	
            	//TODO 发送信息 
                return new ResponseEntity<Transaction>(HttpStatus.OK);
    		}else{
    			return new ResponseEntityBuilder<Transaction>().builder(HttpStatus.BAD_REQUEST, ERROR, "余额不足");
    		}
    	}else{
    		Transaction tran = new Transaction();
        	tran.setCreateDate(new Date());
        	tran.setMoney(money);
        	tran.setStatus(status);
        	tran.setType(type);
        	tran.setUserId(userId);
        	transactionRepository.save(tran);
        	
        	//TODO 发送信息 （此处应考虑充值时，未支付成功之前不发送短信）
            return new ResponseEntity<Transaction>(HttpStatus.OK);
    	}
    }

	@Transactional
	@Override
    public ResponseEntity<Transaction> update(String transactionId,String status,String remark) {
    	logger.info("-----TransactionServiceImpl/update-----");
    	logger.info("transactionId="+transactionId);
    	logger.info("status="+status);
    	logger.info("remark="+remark);
    	
    	Transaction t =transactionRepository.findOne(transactionId);
    	if(null == t){
    		return new ResponseEntityBuilder<Transaction>().builder(HttpStatus.BAD_REQUEST, ERROR, "未查到交易记录");
    	}
    	if(STRING_SUCCESS.equals(t.getStatus()) || STRING_FAILED.equals(t.getStatus())){
    		return new ResponseEntityBuilder<Transaction>().builder(HttpStatus.BAD_REQUEST, ERROR, "状态错误");
    	}

    	ResponseEntity<UserInfo>  REUser = null;
		if(t.getMoney() > 0 && STRING_SUCCESS.equals(status)){
			REUser = userInfoService.updateMoney(t.getUserId(), t.getMoney());
		}
		
		if(t.getMoney() < 0 && STRING_FAILED.equals(status)){
			REUser = userInfoService.updateMoney(t.getUserId(), -t.getMoney());
		}
		
		if(null != REUser && (REUser.getStatusCode() == HttpStatus.OK)){
			transactionRepository.updateStatusByTransactionId(transactionId, status, remark);
			//TODO 发送信息
			return new ResponseEntity<Transaction>(HttpStatus.OK);
		}else{
			return new ResponseEntityBuilder<Transaction>().builder(HttpStatus.BAD_REQUEST, ERROR, "余额不足");
		}
    }

    @Override
    public ResponseEntity<Page<Transaction>> findByUserId(String userId,int page,int size) {
    	logger.info("-----TransactionServiceImpl/findByUserId-----");
    	logger.info("userId="+userId);
    	logger.info("page="+page);
    	logger.info("size="+size);
    	
    	Pageable p = new PageRequest(page, size,Direction.DESC,"createDate");
    	Page<Transaction> reqPage = transactionRepository.findByUserId(userId, p);
    	
        return new ResponseEntity<Page<Transaction>>(reqPage,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<Transaction>> findByStatusAndType(String status, String type,int page,int size) {
    	logger.info("-----TransactionServiceImpl/findByStatusAndType-----");
    	logger.info("status="+status);
    	logger.info("type="+type);
    	logger.info("page="+page);
    	logger.info("size="+size);
    	
    	Pageable p = new PageRequest(page, size,Direction.DESC,"createDate");
    	Page<Transaction> reqPage = transactionRepository.findByStatusAndType(status, type, p);
    	
        return new ResponseEntity<Page<Transaction>>(reqPage,HttpStatus.OK);
    }

}
*/
