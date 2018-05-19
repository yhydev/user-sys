package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.service.MemberSessionService;
import com.gushushu.yanao.usersys.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private ResponseEntity offlinePay(TransactionService.OfflinePayParam offlinePayParam){
        return transactionService.offlinePay(offlinePayParam);
    }

    @RequestMapping("/offlineWithdraw")
    private ResponseEntity offlinePay(TransactionService.OfflineWithdrawParam offlineWithdrawParam){
        return transactionService.offlineWithdraw(offlineWithdrawParam);
    }




    /*@RequestMapping("/member")
    public ResponseEntity getTransaction(TransactionService.SearchParam searchParam,String token){

        memberSessionService.findMemberId(token);



    }
*/


}
