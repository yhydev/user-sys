package com.gushushu.yanao.usersys.service;

import com.gushushu.yanao.usersys.common.PageParam;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.entity.Transaction;
import com.gushushu.yanao.usersys.model.FrontTransaction;
import com.gushushu.yanao.usersys.model.QueryData;
import com.gushushu.yanao.usersys.model.TransactionDetail;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.QBean;
import org.hibernate.sql.Select;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.soap.Detail;
import java.util.Date;
import java.util.List;


public interface TransactionService {

    static final String WAIT_CHECK_STATUS = "wait_check";
    static final String SUCCESS_STATUS = "success";
    static final String FAILED_STATUS = "failed";

    static final String OFFLINE_PAY_TYPE = "offline_deposit";
    static final String OFFLINE_WITHDRAW_TYPE = "offline_withdraw";




    public  <T>  ResponseEntity<ResponseBody<QueryData<T>>>  search(SearchParam searchParam, QBean<T> qBean);


    ResponseEntity<ResponseBody<TransactionDetail>> detail(String transactionId);

    /**
     * 线下支付
     * @param offlineDepositParam
     * @return
     */
    ResponseEntity<ResponseBody<String>> offlineDeposit(OfflineDepositParam offlineDepositParam);


    ResponseEntity<ResponseBody<String>> offlineWithdraw(OfflineWithdrawParam OfflineWithdrawParam);

    //ResponseEntity<ResponseBody<QueryResults<FrontTransaction>>> search(SearchParam searchParam);


    /**
     * 更新交易记录状态
     * @param updateParam
     * @return
     */
    ResponseEntity<ResponseBody<String>> update(UpdateParam updateParam);




    public static class SearchParam extends PageParam {

        private String memberId;
        private String type;
        private String status;

        @Override
        public String toString() {
            return "SearchParam{" +
                    "memberId='" + memberId + '\'' +
                    ", type='" + type + '\'' +
                    ", status='" + status + '\'' +
                    "} " + super.toString();
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
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
        @NotEmpty(message = "token 不能为空")
        private String token;
        @Range(message = "金额必须在 1 - 99999999 元之间",min = 1,max = 999999999)
        @NotNull(message = "金额不能为空")
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

    public static class OfflineDepositParam{
        @Range(message = "金额必须在 1 - 99999999 元之间",min = 1,max = 999999999)
        @NotNull(message = "金额不能为空")
        private Long money;//支付金额
        @NotEmpty(message = "付款账户不能为空")
        private String payAccount;//交易账户
        @NotEmpty(message = "收款账户不能为空")
        private String receiveAccountId;//接收账户
        @NotEmpty(message = "token 不能为空")
        private String token;//交易用户

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "OfflinePayParam{" +
                    "money=" + money +
                    ", payAccount='" + payAccount + '\'' +
                    ", receiveAccountId='" + receiveAccountId + '\'' +
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

        public String getReceiveAccountId() {
            return receiveAccountId;
        }

        public void setReceiveAccountId(String receiveAccountId) {
            this.receiveAccountId = receiveAccountId;
        }
    }


}
