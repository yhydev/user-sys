package com.gushushu.yanao.usersys.service;

import com.gushushu.yanao.usersys.common.PageParam;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.entity.Transaction;
import com.gushushu.yanao.usersys.model.FrontTransaction;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.QBean;
import org.hibernate.sql.Select;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


public interface TransactionService {

    static final String WAIT_CHECK_STATUS = "wait_check";
    static final String SUCCESS_STATUS = "success";
    static final String FAILED_STATUS = "failed";

    static final String OFFLINE_PAY_TYPE = "offline_pay";
    static final String OFFLINE_WITHDRAW_TYPE = "offline_withdraw";




    public  <T>  ResponseEntity<ResponseBody<QueryResults<T>>>  search(SearchParam searchParam,QBean<T> qBean);

    /**
     * 线下支付
     * @param offlinePayParam
     * @return
     */
    ResponseEntity<ResponseBody<String>> offlinePay(OfflinePayParam offlinePayParam);


    ResponseEntity<ResponseBody<String>> offlineWithdraw(OfflineWithdrawParam OfflineWithdrawParam);

    //ResponseEntity<ResponseBody<QueryResults<FrontTransaction>>> search(SearchParam searchParam);


    /**
     * 更新交易记录状态
     * @param updateParam
     * @return
     */
    ResponseEntity<ResponseBody<String>> update(UpdateParam updateParam);




    public static class SearchParam extends PageParam {

        private String userId;
        private String type;
        private String status;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }



    }


    public static class UpdateParam{

        @NotNull
        private Boolean success;
        @NotEmpty
        private String transactionId;

        private String remark;

        @Override
        public String toString() {
            return "UpdateParam{" +
                    "success=" + success +
                    ", transactionId='" + transactionId + '\'' +
                    ", remark='" + remark + '\'' +
                    '}';
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }
    }


    public static class OfflineWithdrawParam{
        private String token;
        private Long money;

        @Override
        public String toString() {
            return "OfflineWithdrawParam{" +
                    "token='" + token + '\'' +
                    ", money='" + money + '\'' +
                    '}';
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Long getMoney() {
            return money;
        }

        public void setMoney(Long money) {
            this.money = money;
        }
    }

    public static class OfflinePayParam{
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
