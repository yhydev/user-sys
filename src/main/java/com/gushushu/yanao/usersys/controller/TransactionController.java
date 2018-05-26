package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.entity.MemberSession;
import com.gushushu.yanao.usersys.service.MemberService;
import com.gushushu.yanao.usersys.service.MemberSessionService;
import com.gushushu.yanao.usersys.service.TransactionService;
import com.gushushu.yanao.usersys.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {


    @Autowired
    private MemberSessionService memberSessionService;

    @Autowired
    private TransactionService transactionService;


    @RequestMapping("/offlinePay")
    public ResponseEntity offlinePay(@Validated TransactionService.OfflinePayParam offlinePayParam){
        return transactionService.offlinePay(offlinePayParam);
    }

    @RequestMapping("/offlineWithdraw")
    public ResponseEntity offlinePay(@Validated TransactionService.OfflineWithdrawParam offlineWithdrawParam){
        return transactionService.offlineWithdraw(offlineWithdrawParam);
    }


    @RequestMapping(path = "/list",params = {"token"})
    public ResponseEntity transactionList(TransactionService.SearchParam searchParam,String token){

        String memberId = memberSessionService.findMemberId(token).getBody().getData();
        searchParam.setMemberId(memberId);
        return transactionService.search(searchParam, TransactionServiceImpl.FRONT_TRANSACTION_Q_BEAN);
    }

    @RequestMapping(path = "/list",params = {"managerToken"})
    public ResponseEntity transactionList(TransactionService.SearchParam searchParam){
        return transactionService.search(searchParam,TransactionServiceImpl.BACK_TRANSACTION_Q_BEAN);
    }



    /*@RequestMapping("/member")
    public ResponseEntity getTransaction(TransactionService.SearchParam searchParam,String token){

        memberSessionService.findMemberId(token);



    }
*/


}
