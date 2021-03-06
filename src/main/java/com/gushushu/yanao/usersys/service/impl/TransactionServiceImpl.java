package com.gushushu.yanao.usersys.service.impl;

import com.gushushu.yanao.usersys.common.QBeans;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.entity.OfflinePay;
import com.gushushu.yanao.usersys.entity.ReceiveAccount;
import com.gushushu.yanao.usersys.entity.Transaction;
import com.gushushu.yanao.usersys.model.BackTransaction;
import com.gushushu.yanao.usersys.model.FrontTransaction;
import com.gushushu.yanao.usersys.model.QueryData;
import com.gushushu.yanao.usersys.model.TransactionDetail;
import com.gushushu.yanao.usersys.repository.OfflinePayRepository;
import com.gushushu.yanao.usersys.repository.ReceiveAccountRepository;
import com.gushushu.yanao.usersys.repository.TransactionRepository;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import com.gushushu.yanao.usersys.service.TransactionService;
import java.util.ArrayList;
import java.util.Date;
import javax.transaction.Transactional;
import com.gushushu.yanao.usersys.service.ValidateService;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import static com.gushushu.yanao.usersys.entity.QTransaction.transaction;

@Service
public class TransactionServiceImpl implements TransactionService {



	final static Logger logger = Logger.getLogger(TransactionServiceImpl.class);

    /**
     * 前端用户查找的列，及查询出的对象
     */
	public static final QBean<FrontTransaction> FRONT_TRANSACTION_Q_BEAN = Projections.bean(
	        FrontTransaction.class,
            transaction.status,
            transaction.type,
            transaction.money,
            transaction.createDate,
            transaction.updateDate,
            transaction.answer,
            transaction.detailId
            );


    /**
     * 后端用户查找的列，及查询出的对象
     */
	public static final QBean<BackTransaction> BACK_TRANSACTION_Q_BEAN = Projections.bean(
	        BackTransaction.class,
            transaction.status,
            transaction.type,
            transaction.money,
            transaction.createDate,
            transaction.updateDate,
            transaction.answer,
            transaction.detailId,
            transaction.transactionId
    );

	@Autowired
	private ReceiveAccountRepository receiveAccountRepository;

	@Autowired
	private OfflinePayRepository offlinePayRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private MemberSessionService memberSessionService;

	@Autowired
	private ValidateService validateService;

	@Autowired
	private JPAQueryFactory jpaQueryFactory;

    private Transaction generate(String memberId,String type,Long money){
        return generate(memberId,type,money,null);
    }

    private Transaction generate(String memberId,String type,Long money,String detailId){

        Transaction transaction = new Transaction();

        transaction.setStatus(WAIT_CHECK_STATUS);
        transaction.setMoney(money);
        transaction.setType(type);
        transaction.setCreateDate(new Date());
        transaction.setMember(new Member(memberId));
        transaction.setCreateDate(new Date());
        if(detailId != null){
            transaction.setDetailId(detailId);
        }

        return transaction;
    }




    @Override
    @Transactional
    public ResponseEntity<ResponseBody<String>> offlineDeposit(OfflineDepositParam offlineDepositParam) {

        logger.info("offlineDepositParam = [" + offlineDepositParam + "]");
        ResponseEntity response = null;
        String errmsg = null;

        ResponseEntity<ResponseBody<String>> bankCardResponse = validateService.bankCard(offlineDepositParam.getPayAccount());

            //支付银行卡是否有效
            if(!bankCardResponse.getBody().isSuccess()){
                errmsg = bankCardResponse.getBody().getMessage();
            }else if(receiveAccountRepository.existsByBankNo(offlineDepositParam.getPayAccount())) {// 支付银行卡是否存在于收款账户
                errmsg = "转账银行卡为无效的卡号";
            }else{
                MemberSessionService.FindOneParam findOneParam =
                        new MemberSessionService.FindOneParam(QBeans.MEMBER_OPEN_ACCOUNT_STATUS);
                findOneParam.setEqToken(offlineDepositParam.getToken());
                ResponseEntity<ResponseBody<QBeans.MemberOpenAccountModel>> findMemberResponse = memberSessionService.findOne(findOneParam);
                QBeans.MemberOpenAccountModel memberOpenAccountModel = findMemberResponse.getBody().getData();

                //用户是否存在
                if(!findMemberResponse.getBody().isSuccess()){
                    errmsg = findMemberResponse.getBody().getMessage();
                }else if(!MemberServiceImpl.MemberOpenAccountStatus.OPEN_ACCOUNT.equals(memberOpenAccountModel.getOpenAccountStatus())){//是否开通交易账户
                    errmsg = "您还未开通交易账户，暂不能交易";
                }else{

                    ReceiveAccount receiveAccount = receiveAccountRepository.findByReceiveAccountId(offlineDepositParam.getReceiveAccountId());
                    if(receiveAccount == null){
                        errmsg = "无效的收款人账户";
                    }else{
                        OfflinePay offlinePay = new OfflinePay();
                        offlinePay.setPayAccount(offlineDepositParam.getPayAccount());
                        offlinePay.setReceiveBankName(receiveAccount.getBankName());
                        offlinePay.setReceiveBankNo(receiveAccount.getBankNo());
                        offlinePay.setReceiveUserName(receiveAccount.getUsername());
                        offlinePayRepository.save(offlinePay);

                        Transaction transaction = generate(memberOpenAccountModel.getMemberId()
                                ,OFFLINE_PAY_TYPE
                                ,offlineDepositParam.getMoney(),offlinePay.getOfflinePayId());

                        transactionRepository.save(transaction);
                        response = ResponseEntityBuilder.success(transaction.getTransactionId());
                    }

                }
            }



        if(errmsg != null){
            response = ResponseEntityBuilder.failed(errmsg) ;
        }

        logger.info("response = " + response);

        return response;
    }




    @Override
    @Transactional
    public ResponseEntity<ResponseBody<String>> offlineWithdraw(OfflineWithdrawParam offlineWithdrawParam) {

        logger.info("offlineWithdrawParam = [" + offlineWithdrawParam + "]");
        ResponseEntity<ResponseBody<String>> response = null;
        String errmsg = null;

        MemberSessionService.FindOneParam findOneParam =
                new MemberSessionService.FindOneParam(QBeans.MEMBER_OPEN_ACCOUNT_STATUS);
        findOneParam.setEqToken(offlineWithdrawParam.getToken());
        ResponseEntity<ResponseBody<QBeans.MemberOpenAccountModel>> rep = memberSessionService.findOne(findOneParam);


        //判断用户是否存在
        if(rep.getBody().isSuccess()){

            QBeans.MemberOpenAccountModel memberOpenAccountModel = rep.getBody().getData();
            //判断用户是否开户
            if(MemberServiceImpl.MemberOpenAccountStatus.OPEN_ACCOUNT
                    .equals(memberOpenAccountModel.getOpenAccountStatus())){

                Transaction transaction = generate(memberOpenAccountModel.getMemberId()
                        ,OFFLINE_WITHDRAW_TYPE,offlineWithdrawParam.getMoney());
                transactionRepository.save(transaction);
                response = ResponseEntityBuilder.success(transaction.getTransactionId());

            }else {
                errmsg = "您还未开通交易账户，暂不能交易";
            }

        }else{
            errmsg = rep.getBody().getMessage();
        }

        if(errmsg != null){
            response = ResponseEntityBuilder.failed(errmsg);
        }

        return response;
    }

    private ArrayList<Predicate> generate(SearchParam searchParam){



        ArrayList<Predicate> predicateList = new ArrayList<>();

        if(!StringUtils.isEmpty(searchParam.getStatus())){
            Predicate predicate = transaction.status.eq(searchParam.getStatus());
            predicateList.add(predicate);
        }

        if(!StringUtils.isEmpty(searchParam.getType())){
            Predicate predicate = transaction.type.eq(searchParam.getType());
            predicateList.add(predicate);
        }

        if(!StringUtils.isEmpty(searchParam.getMemberId())){
            Predicate predicate = transaction.member.memberId.eq(searchParam.getMemberId());
            predicateList.add(predicate);
        }

        return predicateList;
    }





    @Override
    public  <T>  ResponseEntity<ResponseBody<QueryData<T>>>  search(SearchParam searchParam, QBean<T> qBean){

        logger.info("searchParam = [" + searchParam + "]");
        ResponseEntity<ResponseBody<QueryData<T>>> response = null;

        ArrayList<Predicate> predicates = generate(searchParam);
        Predicate[] predicate = new Predicate[predicates.size()];
        predicates.toArray(predicate);

        QueryResults<T> res = jpaQueryFactory.select(qBean)
                .from(transaction)
                .where(predicate)
                .offset(searchParam.getPage() * searchParam.getSize())
                .limit(searchParam.getSize())
                .orderBy(transaction.createDate.desc())
                .fetchResults();

        QueryData<T> ret = new QueryData<T>(res.getResults(),searchParam.getPage(),searchParam.getSize(),res.getTotal());

        response = ResponseEntityBuilder.<QueryData<T>>success(ret);

        logger.info("response = " + response);
        return response;
    }

    @Override
    public ResponseEntity<ResponseBody<TransactionDetail>> detail(String transactionId) {

        System.out.println("transactionId = [" + transactionId + "]");

        Transaction transaction = transactionRepository.findByTransactionId(transactionId);
        TransactionDetail detail = new TransactionDetail();
        String errmsg = null;
        ResponseEntity response = null;

        if(transaction == null){
            errmsg = "交易不存在";
        }else{
            Member member = transaction.getMember();
            //填充用户信息
            detail.setAccount(member.getAccount());
            detail.setInnerDiscAccount(member.getInnerDiscAccount());
            detail.setName(member.getName());
            detail.setMemberBankCard(member.getBankCard());

            //填充交易基本信息
            detail.setMoney(transaction.getMoney());
            detail.setType(transaction.getType());
            detail.setStatus(transaction.getStatus());



            //填充交易详情

            //线下支付信息填充
            if(OFFLINE_PAY_TYPE.equals(transaction.getType())){
                OfflinePay offlinePay = offlinePayRepository.findByOfflinePayId(transaction.getDetailId());

                detail.setPayAccount(offlinePay.getPayAccount());
                detail.setReceiveBankNo(offlinePay.getReceiveBankNo());
                detail.setReceiveBankName(offlinePay.getReceiveBankName());
                detail.setReceiveUsername(offlinePay.getReceiveUserName());
            }



        }


        if(errmsg == null){
            response = ResponseEntityBuilder.success(detail);
        }else{
            response = ResponseEntityBuilder.failed(errmsg);
        }

        System.out.println("response = " + response);

        return response;
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseBody<String>> update(UpdateParam updateParam) {

        logger.info("updateParam = [" + updateParam + "]");
        ResponseEntity<ResponseBody<String>> response = null;
        String errmsg = null;

        //判断交易是否已经审核过
        Transaction transaction = transactionRepository.findByTransactionId(updateParam.getTransactionId());

        if(transaction != null) {
            if(transaction.getUpdateDate() != null || !WAIT_CHECK_STATUS.equals(transaction.getStatus())){
                errmsg = "交易记录已为完成状态。";
            }else{
                transaction.setStatus(updateParam.getSuccess()?SUCCESS_STATUS:FAILED_STATUS);
                transaction.setUpdateDate(new Date());
                transactionRepository.save(transaction);
                response = ResponseEntityBuilder.success(transaction.getTransactionId());
            }
        }else{
            errmsg = "交易不存在";
        }


        if(errmsg != null){
            response = ResponseEntityBuilder.failed(errmsg);
        }

        logger.info("response = " + response);


        return response;
    }







}
