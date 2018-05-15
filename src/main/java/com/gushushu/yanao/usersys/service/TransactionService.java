package com.gushushu.yanao.usersys.service;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;


public interface TransactionService {

    static final String WAIT_CHECK_STATUS = "wait_check";
    static final String SUCCESS_STATUS = "success";
    static final String FAILED_STATUS = "failed";

    static final String UNDER_LINE_PAY_TYPE = "under_line_pay";


    /**
     * 线下支付
     * @param underLinePayParam
     * @return
     */
    ResponseEntity<ResponseBody<String>> underLinePay(UnderLinePayParam underLinePayParam);

    ResponseEntity<Transaction> update(String transactionId,String status,String remark);

    ResponseEntity<Page<Transaction>> findByUserId(String userId,int page,int size);

    ResponseEntity<Page<Transaction>> findByStatusAndType(String status,String type,int page,int size);




    public static class UnderLinePayParam{
        private Long money;//支付金额
        private String payAccount;//交易账户
        private String receiveAccount;//接收账户
        private String token;//交易用户

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "UnderLinePayParam{" +
                    "money=" + money +
                    ", payAccount='" + payAccount + '\'' +
                    ", receiveAccount='" + receiveAccount + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }

        public Long getMoney() {
            return money;
        }

        public void setMoney(Long money) {
            this.money = money;
        }

        public String getPayAccount() {
            return payAccount;
        }

        public void setPayAccount(String payAccount) {
            this.payAccount = payAccount;
        }

        public String getReceiveAccount() {
            return receiveAccount;
        }

        public void setReceiveAccount(String receiveAccount) {
            this.receiveAccount = receiveAccount;
        }


    }


}
