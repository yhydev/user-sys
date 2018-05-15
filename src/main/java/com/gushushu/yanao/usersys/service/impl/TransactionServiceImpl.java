package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.config.AppConstant;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.entity.MemberSession;
import com.gushushu.yanao.usersys.entity.Transaction;
import com.gushushu.yanao.usersys.repository.TransactionRepository;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import com.gushushu.yanao.usersys.service.TransactionService;

import java.util.Date;

import javax.transaction.Transactional;

import com.gushushu.yanao.usersys.service.ValidateService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;




@Service
public class TransactionServiceImpl implements TransactionService,AppConstant {

	final static Logger logger = Logger.getLogger(TransactionServiceImpl.class);
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private MemberSessionService memberSessionService;

	@Autowired
	private ValidateService validateService;

    @Override
    public ResponseEntity<ResponseBody<String>> underLinePay(UnderLinePayParam underLinePayParam) {

        logger.info("underLinePayParam = [" + underLinePayParam + "]");
        ResponseEntity response = null;
        String errmsg = null;
        //支付账户和收款账户是否相同
        if(!underLinePayParam.getPayAccount().equals(underLinePayParam.getReceiveAccount())){

            ResponseEntity<ResponseBody<String>> bankCardResponse = validateService.bankCard(underLinePayParam.getPayAccount());

            //支付银行卡是否有效
            if(bankCardResponse.getBody().isSuccess()){
                ResponseEntity<ResponseBody<Member>> findMemberResponse = memberSessionService.findMember(underLinePayParam.getToken());

                //用户是否存在
                if(findMemberResponse.getBody().isSuccess()){

                    //用户是否开户
                    if(findMemberResponse.getBody().getData().getOpenAccount()){

                        Transaction transaction = new Transaction();
                        transaction.setType(WAIT_CHECK_STATUS);
                        transaction.setStatus(UNDER_LINE_PAY_TYPE);
                        transaction.setCreateDate(new Date());
                        transaction.setPayAccount(underLinePayParam.getPayAccount());
                        transaction.setReceiveAccount(underLinePayParam.getReceiveAccount());
                        transaction.setMember(new Member(findMemberResponse.getBody().getData().getMemberId()));
                        transaction.setStatus(WAIT_CHECK_STATUS);

                        transactionRepository.save(transaction);

                    }else{
                        errmsg = "您还未开通交易账户，暂不能充值";
                    }

                }else{
                    errmsg = findMemberResponse.getBody().getMessage();
                }
            }else{
                errmsg = bankCardResponse.getBody().getMessage();
            }
        }else{
            errmsg = "支付账户不能与收款账户相同";
        }

        if(errmsg != null){
            response = ResponseEntityBuilder.failed(errmsg) ;
        }

        logger.info("response = " + response);

        return response;
    }

    @Override
    public ResponseEntity<Transaction> update(String transactionId, String status, String remark) {
        return null;
    }

    @Override
    public ResponseEntity<Page<Transaction>> findByUserId(String userId, int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<Page<Transaction>> findByStatusAndType(String status, String type, int page, int size) {
        return null;
    }
	
/*
	@Autowired
	private UserInfoService userInfoService;
	
	@Transactional
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
*/

}
