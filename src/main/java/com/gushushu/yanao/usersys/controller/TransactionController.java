package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import com.gushushu.yanao.usersys.service.TransactionService;
import com.gushushu.yanao.usersys.service.impl.MemberServiceImpl;
import com.gushushu.yanao.usersys.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {


    @Autowired
    private MemberSessionService memberSessionService;

    @Autowired
    private TransactionService transactionService;


    @RequestMapping("/offlineDeposit")
    public ResponseEntity offlinePay(@Validated TransactionService.OfflineDepositParam offlineDepositParam){
        return transactionService.offlineDeposit(offlineDepositParam);
    }

    @RequestMapping("/offlineWithdraw")
    public ResponseEntity offlinePay(@Validated TransactionService.OfflineWithdrawParam offlineWithdrawParam){
        return transactionService.offlineWithdraw(offlineWithdrawParam);
    }

    @GetMapping(params = {"transactionId"})
    public ResponseEntity transaction(String transactionId){
        return transactionService.detail(transactionId);
    }

    /**
     * 查询交易列表
     * @param searchParam
     * @param token
     * @return
     */
    @RequestMapping
    public ResponseEntity transactionList(TransactionService.SearchParam searchParam,String token){

        ResponseEntity response = null;

         ResponseBody<Member> findMemberresponse = memberSessionService.findMember(token).getBody();
         if(findMemberresponse.isSuccess()){
             if(findMemberresponse.getData().getType().equals(MemberServiceImpl.USER_TYPE)){
                 //用户查询交易
                 searchParam.setMemberId(findMemberresponse.getData().getMemberId());
                 response = transactionService.search(searchParam,TransactionServiceImpl.FRONT_TRANSACTION_Q_BEAN);
             }else if(findMemberresponse.getData().getType().equals(MemberServiceImpl.MANAGER_TYPE)){
                 //管理员查询交易
                 response = transactionService.search(searchParam,TransactionServiceImpl.BACK_TRANSACTION_Q_BEAN);
             }
         }

        return response;
    }



    /**
     * 更新交易
     * @return
     */
    @PutMapping//TODO 管理员权限
    public ResponseEntity update(TransactionService.UpdateParam updateParam){
        return transactionService.update(updateParam);
    }


    /*@RequestMapping("/member")
    public ResponseEntity getTransaction(TransactionService.SearchParam searchParam,String token){

        memberSessionService.findMemberId(token);



    }
*/


}
